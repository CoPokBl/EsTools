package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayerInventory;
import org.bukkit.inventory.PlayerInventory;

public class FoliaPlayerInventory extends FoliaInventory implements EsPlayerInventory {
    private final PlayerInventory bukkitInv;

    public FoliaPlayerInventory(PlayerInventory inv) {
        super(inv);
        bukkitInv = inv;
    }

    @Override
    public PlayerInventory getBukkitInventory() {
        return bukkitInv;
    }

    @Override
    public EsItemStack getOffHand() {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaItemStack(bukkitInv.getItemInOffHand());
    }

    @Override
    public EsItemStack getMainHand() {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaItemStack(bukkitInv.getItemInMainHand());
    }

    @Override
    public EsItemStack getHelmet() {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaItemStack(bukkitInv.getHelmet());
    }

    @Override
    public EsItemStack getLeggings() {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaItemStack(bukkitInv.getLeggings());
    }

    @Override
    public EsItemStack getChestplate() {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaItemStack(bukkitInv.getChestplate());
    }

    @Override
    public EsItemStack getBoots() {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaItemStack(bukkitInv.getBoots());
    }
}
