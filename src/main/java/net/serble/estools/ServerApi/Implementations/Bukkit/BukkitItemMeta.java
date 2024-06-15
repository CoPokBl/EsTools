package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.EsItemFlag;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsPersistentDataContainer;
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
        bukkitMeta.addItemFlags(convertFlags(flags));
    }

    @Override
    public void removeItemFlags(EsItemFlag... flags) {
        bukkitMeta.removeItemFlags(convertFlags(flags));
    }

    private ItemFlag[] convertFlags(EsItemFlag[] flags) {
        List<ItemFlag> bukkitFlags = new ArrayList<>(flags.length);
        for (EsItemFlag flag : flags) {
            try {
                bukkitFlags.add(ItemFlag.valueOf(flag.name()));
            } catch (IllegalArgumentException ignored) {} // doesnt exist, dont add
        }

        return bukkitFlags.toArray(new ItemFlag[0]);
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

    @Override
    public EsPersistentDataContainer getPersistentDataContainer() {
        return new BukkitPersistentDataContainer(bukkitMeta.getPersistentDataContainer());
    }

    public ItemMeta getBukkitMeta() {
        return bukkitMeta;
    }
}
