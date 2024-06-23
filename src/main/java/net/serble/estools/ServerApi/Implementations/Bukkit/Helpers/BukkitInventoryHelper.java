package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryHolder;

public class BukkitInventoryHelper {

    public static EsInventory createInventory(EsPlayer owner, int size, String title) {
        InventoryHolder holder = owner == null ? null : ((BukkitPlayer) owner).getBukkitPlayer();
        return BukkitHelper.fromBukkitInventory(Bukkit.createInventory(holder, size, title));
    }
}
