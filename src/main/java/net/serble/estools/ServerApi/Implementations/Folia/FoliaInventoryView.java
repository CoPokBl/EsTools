package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitInventoryView;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
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
        return new FoliaInventory(bukkitView.getTopInventory());
    }

    @Override
    public EsInventory getBottomInventory() {
        return new FoliaInventory(bukkitView.getBottomInventory());
    }

    @Override
    public EsInventory getInventory(int slot) {
        return new FoliaInventory(bukkitView.getInventory(slot));
    }

    @Override
    public EsPlayer getPlayer() {
        return new FoliaPlayer((Player) bukkitView.getPlayer());
    }
}
