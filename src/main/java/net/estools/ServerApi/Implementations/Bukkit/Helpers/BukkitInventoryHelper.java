package net.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.estools.ServerApi.EsInventoryType;
import net.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class BukkitInventoryHelper {

    public static EsInventory createInventory(EsPlayer owner, int size, String title) {
        InventoryHolder holder = owner == null ? null : ((BukkitPlayer) owner).getBukkitPlayer();
        return BukkitHelper.fromBukkitInventory(Bukkit.createInventory(holder, size, title));
    }

    public static EsInventory createInventory(EsPlayer owner, EsInventoryType type) {
        InventoryHolder holder = owner == null ? null : ((BukkitPlayer) owner).getBukkitPlayer();
        InventoryType bukkitType = BukkitHelper.toBukkitInventoryType(type);
        if (bukkitType == null) {
            return null;
        }

        return BukkitHelper.fromBukkitInventory(Bukkit.createInventory(holder, bukkitType));
    }
}
