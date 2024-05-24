package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.EsItemFlag;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BukkitItemMeta implements EsItemMeta {
    private final ItemMeta bukkitMeta;

    public BukkitItemMeta(ItemMeta meta) {
        bukkitMeta = meta;
    }

    @Override
    public void setUnbreakable(boolean val) {
        bukkitMeta.setUnbreakable(val);
    }

    @Override
    public boolean isUnbreakable() {
        return bukkitMeta.isUnbreakable();
    }

    @Override
    public Set<EsItemFlag> getItemFlags() {
        Set<ItemFlag> bukkitFlags = bukkitMeta.getItemFlags();
        Set<EsItemFlag> out = new HashSet<>();
        for (ItemFlag flag : bukkitFlags) {
            out.add(EsItemFlag.valueOf(flag.name()));
        }
        return out;
    }

    @Override
    public void addItemFlags(EsItemFlag... flags) {
        ItemFlag[] bukkitFlags = new ItemFlag[flags.length];
        for (int i = 0; i < flags.length; i++) {
            EsItemFlag flag = flags[i];
            bukkitFlags[i] = ItemFlag.valueOf(flag.name());
        }
        bukkitMeta.addItemFlags(bukkitFlags);
    }

    @Override
    public void removeItemFlags(EsItemFlag... flags) {
        ItemFlag[] bukkitFlags = new ItemFlag[flags.length];
        for (int i = 0; i < flags.length; i++) {
            EsItemFlag flag = flags[i];
            bukkitFlags[i] = ItemFlag.valueOf(flag.name());
        }
        bukkitMeta.removeItemFlags(bukkitFlags);
    }

    @Override
    public void setDisplayName(String val) {
        bukkitMeta.setDisplayName(val);
    }

    @Override
    public String getDisplayName() {
        return bukkitMeta.getDisplayName();
    }

    @Override
    public List<String> getLore() {
        if (!bukkitMeta.hasLore()) {
            return new ArrayList<>();
        }
        return bukkitMeta.getLore();
    }

    @Override
    public void setLore(List<String> val) {
        bukkitMeta.setLore(val);
    }

    public ItemMeta getBukkitMeta() {
        return bukkitMeta;
    }
}
