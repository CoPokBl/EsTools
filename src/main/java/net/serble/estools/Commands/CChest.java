package net.serble.estools.Commands;

import net.serble.estools.Config.ConfigManager;
import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.*;
import net.serble.estools.ServerApi.Events.*;
import net.serble.estools.ServerApi.Interfaces.*;

import java.util.*;

public class CChest extends EsToolsCommand implements EsEventListener {

	public static HashMap<UUID, EsInventory> cChests = new HashMap<>();

	@Override
	public void onEnable() {
		if (Main.minecraftVersion.getMinor() > 4) {
			Main.registerEvents(this);
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
            inv = loadPlayer(p);
        }
		
		p.closeInventory();
		p.openInventory(inv);
		
		cChests.put(uid, inv);
		return true;
	}

	private void onInventoryClick(final EsInventoryClickEvent e) {
		UUID uid = e.getPlayer().getUniqueId();
		EsItemStack currentItem = e.getCurrentItem();

		// Check if inventory is cChest
        if (!cChests.containsKey(uid) || !e.getInventory().isEqualTo(cChests.get(uid)) || e.getClickedInventory() == null) {
            return;
        }

        if (e.getAction().equals(EsInventoryAction.CollectToCursor)) {
            e.setCancelled(true);

            EsItemStack cursor = e.getCursor();
			if (cursor == null) {
				return;
			}

			EsInventory pInv = e.getPlayer().getInventory();
            for (Map.Entry<Integer, EsItemStack> i : pInv.all(cursor.getType()).entrySet()) {
				EsItemStack item = i.getValue();

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
        if (!e.getClickedInventory().isEqualTo(cChests.get(uid))) {
			// Shift click into cChest
			if (equalsOr(e.getClick(), EsClickType.ShiftLeft, EsClickType.ShiftRight) && currentItem != null) {
				e.setCancelled(true);

				e.getInventory().addItem(currentItem.clone());
			}

			return;
        }

		// Right click in cChest (clear item)
		if (e.getClick().equals(EsClickType.Right) && currentItem != null) {
			e.setCancelled(true);
			e.getClickedInventory().setItem(e.getSlot(), null);
			return;
		}

		// Add to chest
		if (equalsOr(e.getAction(), EsInventoryAction.PlaceAll, EsInventoryAction.PlaceOne, EsInventoryAction.PlaceSome)) {
			e.setCancelled(true);
			EsItemStack item = e.getCursor();
			if (item == null) {
				return;
			}

			EsItemStack finalItem = item.clone();

			if (equalsOr(e.getAction(), EsInventoryAction.PlaceOne, EsInventoryAction.PlaceSome)) {
				finalItem.setAmount(1);
			}

			setItemTask(e.getInventory(), e.getSlot(), finalItem);
			return;
		}

		// Remove from cChest
		if (equalsOr(e.getClick(), EsClickType.Left, EsClickType.Drop, EsClickType.ControlDrop,
				EsClickType.ShiftLeft, EsClickType.ShiftRight) && currentItem != null) {
			EsItemStack item = currentItem.clone();
			setItemTask(e.getInventory(), e.getSlot(), item);
		}
    }

	private static void setItemTask(EsInventory inv, int slot, EsItemStack item) {
		Main.server.runTask(() -> inv.setItem(slot, item));
	}
	
	// Cancel drag if inside cChest
    private void onInventoryDrag(final EsInventoryDragEvent e) {
		EsInventory inv = cChests.get(e.getPlayer().getUniqueId());
        if (!e.getInventory().isEqualTo(inv)) {
            return;
        }

        // Check if any of the slots dragged are in the cChest
        for (int slot : e.getChangedSlots()) {
            if (e.getView().getInventory(slot).isEqualTo(inv)) {
                e.setCancelled(true);
                return;
            }
        }
    }
	
	public static EsInventory loadPlayer(EsPlayer p) {
		UUID uid = p.getUniqueId();
		
		@SuppressWarnings("unchecked")
		List<EsSerialisableItemStack> f = (List<EsSerialisableItemStack>) ConfigManager.load(
				"cchests/" + uid + ".yml",
				ArrayList.class,
				EsSerialisableItemStack.class);
        EsItemStack[] content = new EsItemStack[f.size()];
		for (int i = 0; i < content.length; i++) {
			if (f.get(i) == null) {
				content[i] = null;
				continue;
			}
			content[i] = f.get(i).toItemStack();
		}

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

		EsInventory inv = cChests.get(uid);
		EsSerialisableItemStack[] contents = new EsSerialisableItemStack[inv.getContents().length];
		for (int i = 0; i < inv.getContents().length; i++) {
			EsItemStack stack = inv.getContents()[i];
			contents[i] = stack == null ? null : EsSerialisableItemStack.generate(stack);
		}

		ConfigManager.save("cchests/" + uid + ".yml", contents);
	}

	private void onClose(EsInventoryCloseEvent e) {
		UUID uid = e.getPlayer().getUniqueId();

		if (e.getInventory().isEqualTo(cChests.get(uid))) {
			savePlayer(e.getPlayer());
		}
	}

	private void onQuit(EsPlayerQuitEvent e) {
		savePlayer(e.getPlayer());
		cChests.remove(e.getPlayer().getUniqueId());
	}

	private void onKick(EsPlayerKickEvent e) {
		savePlayer(e.getPlayer());
		cChests.remove(e.getPlayer().getUniqueId());
	}

	@Override
	public void executeEvent(EsEvent event) {
		if (event instanceof EsPlayerQuitEvent) {
			onQuit((EsPlayerQuitEvent) event);
			return;
		}

		if (event instanceof EsPlayerKickEvent) {
			onKick((EsPlayerKickEvent) event);
			return;
		}

		if (event instanceof EsInventoryClickEvent) {
			onInventoryClick((EsInventoryClickEvent) event);
			return;
		}

		if (event instanceof EsInventoryCloseEvent) {
			onClose((EsInventoryCloseEvent) event);
			return;
		}

		if (event instanceof EsInventoryDragEvent) {
			onInventoryDrag((EsInventoryDragEvent) event);
		}
	}
}
