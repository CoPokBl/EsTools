package net.serble.estools.ServerApi.Implementations.Bukkit.EventHandlers;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Events.EsInventoryDragEvent;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitInventoryView;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsInventoryView;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Set;

public class BukkitEventsListenerPost1_4 extends BukkitEventsListener {

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
