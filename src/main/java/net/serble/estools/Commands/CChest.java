package net.serble.estools.Commands;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.EsToolsCommand;
import net.serble.estools.ConfigManager;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsGameMode;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitInventory;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

// TODO: This class has config and events
public class CChest extends EsToolsCommand implements Listener {

	public static HashMap<UUID, EsInventory> cChests = new HashMap<>();

	@Override
	public void onEnable() {
		if (Main.minecraftVersion.getMinor() > 4) {
			Bukkit.getServer().getPluginManager().registerEvents(this, EsToolsBukkit.plugin);
		}
	}

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }

		if (Main.minecraftVersion.getMinor() < 7) {
			send(sender, "&cWarning: CChest doesn't work to full capacity");
		}

		EsPlayer p = (EsPlayer) sender;
		
		if (p.getGameMode().equals(EsGameMode.Creative)) {
			if (checkPerms(sender, "cchest.creative")) {
                return false;
            }
		} else {
			if (checkPerms(sender, "cchest.survival")) {
                return false;
            }
		}

		UUID uid = p.getUniqueId();
		EsInventory inv = cChests.get(uid);
		
		if (inv == null) {
			EsInventory loaded = loadPlayer(p);
			if (loaded != null) {
				inv = loaded;
			} else {
				inv = Main.server.createInventory(null, 54, translate("&1Creative Chest"));
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
        if (!cChests.containsKey(uid) || !isSameInv(e.getInventory(), cChests.get(uid)) || e.getClickedInventory() == null) {
            return;
        }

        if (e.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
            e.setCancelled(true);

            ItemStack cursor = e.getCursor();
			if (cursor == null) {
				return;
			}

			org.bukkit.inventory.Inventory pInv = e.getWhoClicked().getInventory();
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
        if (!e.getClickedInventory().equals(((BukkitInventory)cChests.get(uid)).getBukkitInventory())) {
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

	private static void setItemTask(org.bukkit.inventory.Inventory inv, int slot, ItemStack item) {
		Bukkit.getScheduler().runTask(EsToolsBukkit.plugin, () -> inv.setItem(slot, item));
	}

	// TODO: Remove reliance on Bukkit
	private static boolean isSameInv(Inventory a, EsInventory b) {
		return a.equals(((BukkitInventory) b).getBukkitInventory());
	}
	
	// Cancel drag if inside cChest
	@EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
		EsInventory inv = cChests.get(e.getWhoClicked().getUniqueId());
        if (!isSameInv(e.getInventory(), inv)) {
            return;
        }

        // Check if any of the slots dragged are in the cChest
        for (int slot : e.getRawSlots()) {
            if (isSameInv(Objects.requireNonNull(e.getView().getInventory(slot)), inv)) {
                e.setCancelled(true);
                return;
            }
        }
    }
	
	public static EsInventory loadPlayer(EsPlayer p) {
		UUID uid = p.getUniqueId();
		
		FileConfiguration f = ConfigManager.load("cchests/" + uid + ".yml");

        if (!f.contains("items")) {
            return null;
        }

        @SuppressWarnings("unchecked")
        EsItemStack[] content = ((ArrayList<EsItemStack>) Objects.requireNonNull(f.get("items"))).toArray(new EsItemStack[0]);

        EsInventory inv = Main.server.createInventory(null, 54, translate("&1Creative Chest"));
        inv.setContents(content);
        cChests.put(uid, inv);

        return inv;
    }
	
	public static void savePlayer(EsPlayer p) {
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

		if (isSameInv(e.getInventory(), cChests.get(uid))) {
			savePlayer((EsPlayer)e.getPlayer());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		savePlayer(new BukkitPlayer(e.getPlayer()));
		cChests.remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		savePlayer(new BukkitPlayer(e.getPlayer()));
		cChests.remove(e.getPlayer().getUniqueId());
	}
}
