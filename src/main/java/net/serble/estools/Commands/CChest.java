package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
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

import java.util.*;

public class CChest extends EsToolsCommand implements Listener {

	public static HashMap<UUID, Inventory> cChests = new HashMap<>();

	@Override
	public void onEnable() {
		if (Main.majorVersion > 4) {
			Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }

		if (Main.majorVersion < 7) {
			send(sender, "&cWarning: CChest doesn't work to full capacity");
		}

		Player p = (Player)sender;
		
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			if (checkPerms(sender, "cchest.creative")) {
                return false;
            }
		} else {
			if (checkPerms(sender, "cchest.survival")) {
                return false;
            }
		}

		UUID uid = p.getUniqueId();
		Inventory inv = cChests.get(uid);
		
		if (inv == null) {
			Inventory loaded = loadPlayer(p);
			if (loaded != null) {
				inv = loaded;
			} else {
				inv = Bukkit.createInventory(null, 54, translate("&1Creative Chest"));
			}
		}
		
		p.closeInventory();
		p.openInventory(inv);
		
		cChests.put(uid, inv);
		return true;
	}
	
	@EventHandler
	public void inventoryClick(final InventoryClickEvent e) {
		UUID uid = e.getWhoClicked().getUniqueId();
		ItemStack currentItem = e.getCurrentItem();
		
		// Check if inventory is cChest
        if (!cChests.containsKey(uid) || !cChests.get(uid).equals(e.getInventory()) || e.getClickedInventory() == null) {
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

		// If player inventory
        if (!e.getClickedInventory().equals(cChests.get(uid))) {
			// Shift click into cChest
			if (equalsOr(e.getClick(), ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT) && currentItem != null) {
				e.setCancelled(true);

				e.getInventory().addItem(currentItem.clone());
			}

			return;
        }

		// Right click in cChest (clear item)
		if (e.getClick().equals(ClickType.RIGHT) && currentItem != null) {
			e.setCancelled(true);
			e.getClickedInventory().setItem(e.getSlot(), null);
			return;
		}

		// Add to chest
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

		// Remove from cChest
		if (equalsOr(e.getClick(), ClickType.LEFT, ClickType.DROP, ClickType.CONTROL_DROP,
				ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT) && currentItem != null) {
			ItemStack item = currentItem.clone();
			setItemTask(e.getInventory(), e.getSlot(), item);
		}
    }

	private static void setItemTask(Inventory inv, int slot, ItemStack item) {
		Bukkit.getScheduler().runTask(Main.plugin, () -> inv.setItem(slot, item));
	}
	
	// Cancel drag if inside cChest
	@EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
		Inventory inv = cChests.get(e.getWhoClicked().getUniqueId());
        if (!e.getInventory().equals(inv)) {
            return;
        }

        // Check if any of the slots dragged are in the cChest
        for (int slot : e.getRawSlots()) {
            if (inv.equals(e.getView().getInventory(slot))) {
                e.setCancelled(true);
                return;
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

        Inventory inv = Bukkit.createInventory(null, 54, translate("&1Creative Chest"));
        inv.setContents(content);
        cChests.put(uid, inv);

        return inv;
    }
	
	public static void savePlayer(Player p) {
		UUID uid = p.getUniqueId();
		
		if (!cChests.containsKey(uid)) {
			return;
		}
		
		FileConfiguration f = new YamlConfiguration();
		f.set("items", cChests.get(uid).getContents());
		ConfigManager.save("cchests/" + uid + ".yml", f);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		UUID uid = e.getPlayer().getUniqueId();

		if (e.getInventory().equals(cChests.get(uid))) {
			savePlayer((Player)e.getPlayer());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		savePlayer(e.getPlayer());
		cChests.remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		savePlayer(e.getPlayer());
		cChests.remove(e.getPlayer().getUniqueId());
	}
}
