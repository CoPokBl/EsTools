package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.Implementations.Bukkit.BukkitInventoryView;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class FoliaInventoryView extends BukkitInventoryView {
    private final InventoryView bukkitView;

    public FoliaInventoryView(InventoryView child) {
        super(child);
        bukkitView = child;
    }

    @Override
    public EsInventory getTopInventory() {
        return FoliaHelper.fromBukkitInventory(bukkitView.getTopInventory());
    }

    @Override
    public EsInventory getBottomInventory() {
        return FoliaHelper.fromBukkitInventory(bukkitView.getBottomInventory());
    }

    @Override
    public EsInventory getInventory(int slot) {
        return FoliaHelper.fromBukkitInventory(bukkitView.getInventory(slot));
    }

    @Override
    public EsPlayer getPlayer() {
        return new FoliaPlayer((Player) bukkitView.getPlayer());
    }
}
