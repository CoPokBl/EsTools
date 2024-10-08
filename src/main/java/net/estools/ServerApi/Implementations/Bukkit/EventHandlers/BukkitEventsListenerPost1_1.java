package net.estools.ServerApi.Implementations.Bukkit.EventHandlers;

import net.estools.Main;
import net.estools.ServerApi.EsClickType;
import net.estools.ServerApi.EsInventoryAction;
import net.estools.ServerApi.Events.EsInventoryClickEvent;
import net.estools.ServerApi.Events.EsInventoryCloseEvent;
import net.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitInventoryClickHelper;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

// Apparently inheriting BukkitEventsListener stops the events from working in BukkitEventsListener
public class BukkitEventsListenerPost1_1 implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        EsInventory clInv;
        if (Main.minecraftVersion.isAtLeast(1, 8, 0)) {
            clInv = BukkitHelper.fromBukkitInventory(e.getClickedInventory());
        } else {
            clInv = BukkitHelper.fromBukkitInventory(e.getInventory());  // Get clicked inv doesn't exist
        }

        EsInventory inv = BukkitHelper.fromBukkitInventory(e.getInventory());

        EsClickType ct;
        if (Main.minecraftVersion.isAtLeast(1, 5, 0)) {
            ct = BukkitInventoryClickHelper.fromBukkitClickType(e.getClick());
        } else {
            ct = EsClickType.Unknown;  // getClick() doesn't exist in 1.4.x
        }

        EsInventoryAction ac;
        if (Main.minecraftVersion.isAtLeast(1, 5, 0)) {
            ac = BukkitInventoryClickHelper.fromBukkitInventoryAction(e.getAction());
        } else {
            ac = EsInventoryAction.Unknown;  // getAction() doesn't exist in 1.4.x
        }

        EsItemStack ci = BukkitHelper.fromBukkitItem(e.getCurrentItem());
        EsPlayer cl = new BukkitPlayer((Player) e.getWhoClicked());
        EsItemStack cu = BukkitHelper.fromBukkitItem(e.getCursor());
        int sl = e.getSlot();
        EsInventoryClickEvent ee = new EsInventoryClickEvent(cl, sl, cu, inv, clInv, ci, ac, ct);
        ee.setCancelled(e.isCancelled());
        boolean wasCancelled = e.isCancelled();
        Main.callEvent(ee);
        if (wasCancelled != ee.isCancelled()) {  // Fix bug where it cancels
            e.setCancelled(ee.isCancelled());
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        EsInventory inv = BukkitHelper.fromBukkitInventory(e.getInventory());
        EsPlayer pl = new BukkitPlayer((Player) e.getPlayer());
        EsInventoryCloseEvent ee = new EsInventoryCloseEvent(pl, inv);
        Main.callEvent(ee);
    }
}
