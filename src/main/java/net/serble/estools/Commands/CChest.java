package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.ConfigManager;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CChest extends CMD implements Listener {

	public static HashMap<UUID, Inventory> cchests = new HashMap<UUID, Inventory>();
	public static HashMap<UUID, ItemStack> heldItem = new HashMap<UUID, ItemStack>();

	@Override
	public void onEnable() {
		if (Main.version > 4)
			Bukkit.getServer().getPluginManager().registerEvents(this, Main.current);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return true;

		if (Main.version < 7) {
			s(sender, "&cWarning: CCHest doesnt work to full capacity");
		}

		Player p = (Player)sender;
		
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			if (checkPerms(sender, "cchest.creative"))
				return false;
		} else {
			if (checkPerms(sender, "cchest.survival"))
				return false;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, t("&1Creative Chest"));
		
		UUID puid = p.getUniqueId();
		
		if (cchests.containsKey(puid)) {
			inv = cchests.get(puid);
		}
		
		p.closeInventory();
		p.openInventory(inv);
		
		cchests.put(puid, inv);
		
		return true;
	}
	
	@EventHandler
	public void onInventory(final InventoryClickEvent e) {
		UUID puid = e.getWhoClicked().getUniqueId();
		
		ItemStack cItem = e.getCurrentItem();
		
		// check if inventory is cchest
		if (cchests.containsKey(puid) && cchests.get(puid).equals(e.getInventory())) {
			
			if (e.getClickedInventory() == null) return;
			
			if (equalsOr(e.getAction(), InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_HALF,
										InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_SOME)) {

				heldItem.put(puid, cItem.clone());
			}
			
			if (e.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
				e.setCancelled(true);
			}
			
			// if they clicked cchest:
			if (e.getClickedInventory().equals(cchests.get(puid))) {		
				
				// right click on chest
				if (e.getClick().equals(ClickType.RIGHT)) {
					e.setCancelled(true);
					
					if (cItem != null) {
						e.getClickedInventory().setItem(e.getSlot(), null);
					}
				}
				
				// left click or drop on chest
				else if (equalsOr(e.getClick(), ClickType.LEFT, ClickType.DROP, ClickType.CONTROL_DROP)) {
					if (e.getAction().equals(InventoryAction.PLACE_ALL)) {
						e.setCancelled(true);
						
						new BukkitRunnable() {
							@Override
							public void run() {
								e.getInventory().setItem(e.getRawSlot(), heldItem.get(puid));
							}
						}.runTaskLater(Main.current, 0);
						
					} else {
						new BukkitRunnable() {
							@Override
							public void run() {
								if (cItem != null)
									e.getInventory().setItem(e.getRawSlot(), cItem.clone());
							}
						}.runTaskLater(Main.current, 0);
					}
				}

				// shift click on cchest
				else if (equalsOr(e.getClick(), ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT)) {
					e.setCancelled(true);
					if (cItem != null)
						e.getWhoClicked().getInventory().addItem(cItem.clone());
				}
			}
			
			// if player inventory
			else {
				if (equalsOr(e.getAction(), InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, 
						InventoryAction.PLACE_SOME)) {

					heldItem.remove(puid);
				}
			}
		}
	}
	
	// cancel drag if cchest
	@EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
		UUID puid = e.getWhoClicked().getUniqueId();
		
		if (cchests.containsKey(puid)) {
			if (cchests.get(puid).equals(e.getInventory())) {
				e.setCancelled(true);
			}
		}
    }

//    // Cancel if dropping items from cchest
//    @EventHandler
//	public void onDrop(final PlayerDropItemEvent e) {
//		if (e.getPlayer().getOpenInventory().getTopInventory().equals(cchests.get(e.getPlayer().getUniqueId()))) {
//			e.setCancelled(true);
//		}
//	}
	
	public static void loadPlayer(Player p) {		
		UUID puid = p.getUniqueId();
		
		FileConfiguration f = ConfigManager.load("cchests/" + puid.toString() + ".yml");
		
		if (f.contains("items")) {
			@SuppressWarnings("unchecked")
			ArrayList<ItemStack> content = (ArrayList<ItemStack>) f.get("items");
			
			ItemStack[] cA = new ItemStack[content.size()];
			
			cA = content.toArray(cA);
			
			Inventory inv = Bukkit.createInventory(null, 54, t("&1Creative Chest"));
			inv.setContents(cA);
			cchests.put(puid, inv);
		}
	}
	
	public static void savePlayer(Player p) {		
		UUID puid = p.getUniqueId();
		
		if (!cchests.containsKey(puid)) return;
		
		FileConfiguration f = new YamlConfiguration();

		f.set("items", cchests.get(puid).getContents());
		
		ConfigManager.save("cchests/" + puid.toString() + ".yml", f);
		
		cchests.remove(p.getUniqueId());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) { savePlayer(e.getPlayer()); }
	
	@EventHandler
	public void onKick(PlayerKickEvent e) { savePlayer(e.getPlayer()); }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) { loadPlayer(e.getPlayer()); }
}
