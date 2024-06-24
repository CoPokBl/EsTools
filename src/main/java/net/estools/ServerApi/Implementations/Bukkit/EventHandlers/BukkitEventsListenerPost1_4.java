package net.estools.ServerApi.Implementations.Bukkit.EventHandlers;

import net.estools.Main;
import net.estools.ServerApi.Events.EsInventoryDragEvent;
import net.estools.ServerApi.Implementations.Bukkit.BukkitInventoryView;
import net.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsInventoryView;
import net.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Set;

// Apparently inheriting BukkitEventsListener stops the events from working in BukkitEventsListener
public class BukkitEventsListenerPost1_4 implements Listener {

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        EsInventory inv = BukkitHelper.fromBukkitInventory(e.getInventory());
        EsPlayer pl = new BukkitPlayer((Player) e.getWhoClicked());
        Set<Integer> cs = e.getRawSlots();
        EsInventoryView view = new BukkitInventoryView(e.getView());
        EsInventoryDragEvent ee = new EsInventoryDragEvent(pl, inv, cs, view);
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }
}
