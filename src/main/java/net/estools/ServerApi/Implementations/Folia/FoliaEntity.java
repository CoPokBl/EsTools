package net.estools.ServerApi.Implementations.Folia;

import io.papermc.lib.PaperLib;
import net.estools.Main;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Implementations.Bukkit.BukkitEntity;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsEntity;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class FoliaEntity extends BukkitEntity {
    private final Entity bukkitEntity;

    public FoliaEntity(Entity bukkitEntity) {
        super(bukkitEntity);
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public List<EsEntity> getPassengers() {
        List<Entity> bukkitList = bukkitEntity.getPassengers();
        List<EsEntity> list = new ArrayList<>();
        for (Entity entity : bukkitList) {
            list.add(BukkitHelper.fromBukkitEntity(entity));
        }
        return list;
    }

    @Override
    public void addPassenger(EsEntity entity) {
        if (Main.minecraftVersion.getMinor() > 11) {
            bukkitEntity.addPassenger(((FoliaEntity) entity).getBukkitEntity());
        } else {
            //noinspection deprecation
            bukkitEntity.setPassenger(((FoliaEntity) entity).getBukkitEntity());
        }
    }

    @Override
    public EsLocation getLocation() {
        return FoliaHelper.fromBukkitLocation(bukkitEntity.getLocation());
    }

    @Override
    public void teleport(EsLocation loc) {
        PaperLib.teleportAsync(bukkitEntity, FoliaHelper.toBukkitLocation(loc));
    }
}
