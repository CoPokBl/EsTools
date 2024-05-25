package net.serble.estools.ServerApi.Implementations.Folia;

import io.papermc.lib.PaperLib;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitEntity;
import org.bukkit.entity.Entity;

public class FoliaEntity extends BukkitEntity {
    private final org.bukkit.entity.Entity bukkitEntity;

    public FoliaEntity(Entity bukkitEntity) {
        super(bukkitEntity);
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public void teleport(EsLocation loc) {
        PaperLib.teleportAsync(bukkitEntity, FoliaHelper.toBukkitLocation(loc));
    }
}
