package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsInventoryView;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class BukkitInventoryView implements EsInventoryView {
    private final InventoryView bukkitView;

    public BukkitInventoryView(InventoryView child) {
        bukkitView = child;
    }

    @Override
    public EsInventory getTopInventory() {
        return BukkitHelper.fromBukkitInventory(bukkitView.getTopInventory());
    }

    @Override
    public EsInventory getBottomInventory() {
        return BukkitHelper.fromBukkitInventory(bukkitView.getBottomInventory());
    }

    @Override
    public EsInventory getInventory(int slot) {
        if (Main.minecraftVersion.getMinor() <= 12) {  // .getInventory(slot) doesn't exist
            if (slot >= getTopInventory().getSize()) {  // Bottom inv
                return getBottomInventory();
            }
            return getTopInventory();
        }
        return BukkitHelper.fromBukkitInventory(bukkitView.getInventory(slot));
    }

    @Override
    public EsPlayer getPlayer() {
        return new BukkitPlayer((Player) bukkitView.getPlayer());
    }
}
