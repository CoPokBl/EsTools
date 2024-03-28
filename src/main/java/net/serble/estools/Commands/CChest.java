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
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CChest extends CMD implements Listener {

	public static HashMap<UUID, Inventory> cchests = new HashMap<>();

	@Override
	public void onEnable() {
		if (Main.version > 4) {
			Bukkit.getServer().getPluginManager().registerEvents(this, Main.current);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender))
			return true;

		if (Main.version < 7) {
			s(sender, "&cWarning: CChest doesn't work to full capacity");
		}

		Player p = (Player)sender;
		
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			if (checkPerms(sender, "cchest.creative"))
				return false;
		} else {
			if (checkPerms(sender, "cchest.survival"))
				return false;
		}

		UUID uid = p.getUniqueId();
		Inventory inv = cchests.get(uid);
		
		if (inv == null) {
			Inventory loaded = loadPlayer(p);
			if (loaded != null) {
				inv = loaded;
			} else {
				inv = Bukkit.createInventory(null, 54, t("&1Creative Chest"));
			}
		}
		
		p.closeInventory();
		p.openInventory(inv);
		
		cchests.put(uid, inv);
		return true;
	}
	
	@EventHandler
	public void inventoryClick(final InventoryClickEvent e) {
		UUID uid = e.getWhoClicked().getUniqueId();
		ItemStack currentItem = e.getCurrentItem();
		
		// check if inventory is cchest
        if (!cchests.containsKey(uid) || !cchests.get(uid).equals(e.getInventory()) || e.getClickedInventory() == null) {
            return;
        }

        if (e.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
            e.setCancelled(true);

            ItemStack cursor = e.getCursor();
			if (cursor == null) {
				return;
			}

			Inventory pInv = e.getWhoClicked().getInventory();
            for (Map.Entry<Integer, ? extends ItemStack> i : pInv.all(cursor.getType()).entrySet()) {
				ItemStack item = i.getValue();

				if (item == null) {
					continue;
				}

				if (cursor.isSimilar(item)) {
					cursor.setAmount(cursor.getAmount() + item.getAmount());

					if (cursor.getAmount() > cursor.getMaxStackSize()) {
						item.setAmount(cursor.getAmount() - cursor.getMaxStackSize());
						cursor.setAmount(cursor.getMaxStackSize());
					} else {
						pInv.setItem(i.getKey(), null);
					}
				}
			}
			return;
        }

		// if player inventory
        if (!e.getClickedInventory().equals(cchests.get(uid))) {
			// shift click into cchest
			if (equalsOr(e.getClick(), ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT) && currentItem != null) {
				e.setCancelled(true);

				e.getInventory().addItem(currentItem.clone());
			}

			return;
        }

		// right click in cchest (clear item)
		if (e.getClick().equals(ClickType.RIGHT) && currentItem != null) {
			e.setCancelled(true);
			e.getClickedInventory().setItem(e.getSlot(), null);
			return;
		}

		// add to chest
		if (equalsOr(e.getAction(), InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME)) {
			e.setCancelled(true);
			ItemStack item = e.getCursor();
			if (item == null) {
				return;
			}

			ItemStack finalItem = item.clone();

			if (equalsOr(e.getAction(), InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME)) {
				finalItem.setAmount(1);
			}

			setItemTask(e.getInventory(), e.getSlot(), finalItem);
			return;
		}

		// remove from cchest
		if (equalsOr(e.getClick(), ClickType.LEFT, ClickType.DROP, ClickType.CONTROL_DROP,
				ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT) && currentItem != null) {
			ItemStack item = currentItem.clone();
			setItemTask(e.getInventory(), e.getSlot(), item);
		}
    }

	private static void setItemTask(Inventory inv, int slot, ItemStack item) {
		new BukkitRunnable() {
			@Override
			public void run() {
				inv.setItem(slot, item);
			}
		}.runTask(Main.current);
	}
	
	// cancel drag if inside cchest
	@EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
		Inventory inv = cchests.get(e.getWhoClicked().getUniqueId());

		if (inv.equals(e.getInventory())) {
			// check if any of the slots dragged are in the cchest
			for (int slot : e.getRawSlots()) {
				if (inv.equals(e.getView().getInventory(slot))) {
					e.setCancelled(true);
					return;
				}
			}
		}
    }
	
	public static Inventory loadPlayer(Player p) {
		UUID uid = p.getUniqueId();
		
		FileConfiguration f = ConfigManager.load("cchests/" + uid + ".yml");

        if (!f.contains("items")) {
            return null;
        }

        @SuppressWarnings("unchecked")
        ItemStack[] content = ((ArrayList<ItemStack>) Objects.requireNonNull(f.get("items"))).toArray(new ItemStack[0]);

        Inventory inv = Bukkit.createInventory(null, 54, t("&1Creative Chest"));
        inv.setContents(content);
        cchests.put(uid, inv);

        return inv;
    }
	
	public static void savePlayer(Player p) {		
		UUID uid = p.getUniqueId();
		
		if (!cchests.containsKey(uid)) {
			return;
		}
		
		FileConfiguration f = new YamlConfiguration();
		f.set("items", cchests.get(uid).getContents());
		ConfigManager.save("cchests/" + uid + ".yml", f);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		UUID uid = e.getPlayer().getUniqueId();

		if (e.getInventory().equals(cchests.get(uid))) {
			savePlayer((Player)e.getPlayer());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		savePlayer(e.getPlayer());
		cchests.remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		savePlayer(e.getPlayer());
		cchests.remove(e.getPlayer().getUniqueId());
	}
}
