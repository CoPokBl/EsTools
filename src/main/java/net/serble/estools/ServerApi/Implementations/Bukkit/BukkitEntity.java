package net.serble.estools.ServerApi.Implementations.Bukkit;

import io.papermc.lib.PaperLib;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static net.serble.estools.ServerApi.Implementations.Bukkit.BukkitHelper.toBukkitLocation;

public class BukkitEntity implements EsEntity {
    private final org.bukkit.entity.Entity bukkitEntity;

    public BukkitEntity(org.bukkit.entity.Entity bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
    }

    public Entity getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public void teleport(EsLocation loc) {
        PaperLib.teleportAsync(bukkitEntity, BukkitHelper.toBukkitLocation(loc));
    }

    @Override
    public String getType() {
        return bukkitEntity.getType().name();
    }

    @Override
    public String getName() {
        if (Main.minecraftVersion.getMinor() > 7) {
            return bukkitEntity.getName();
        }

        if (bukkitEntity instanceof Player) {
            return ((Player)bukkitEntity).getDisplayName();
        }

        if (bukkitEntity instanceof LivingEntity) {
            String name = ((LivingEntity)bukkitEntity).getCustomName();
            if (name != null) {
                return name;
            }
        }

        return "Entity";
    }

    @Override
    public UUID getUniqueId() {
        return bukkitEntity.getUniqueId();
    }

    @Override
    public boolean hasPermission(String node) {
        return bukkitEntity.hasPermission(node);
    }

    @Override
    public boolean isPermissionSet(String node) {
        return bukkitEntity.isPermissionSet(node);
    }

    @Override
    public void sendMessage(String... msg) {
        bukkitEntity.sendMessage(msg);
    }

    @Override
    public EsLocation getLocation() {
        return BukkitHelper.fromBukkitLocation(bukkitEntity.getLocation());
    }

    @Override
    public boolean leaveVehicle() {
        if (bukkitEntity instanceof LivingEntity) {
            // Not redundant for below 1.3
            //noinspection RedundantCast
            return ((LivingEntity) bukkitEntity).leaveVehicle();
        } else {
            return bukkitEntity.leaveVehicle();
        }
    }

    @Override
    public List<EsEntity> getPassengers() {
        List<Entity> bukkitList = bukkitEntity.getPassengers();
        List<EsEntity> list = new ArrayList<>();
        for (Entity entity : bukkitList) {
            list.add(new BukkitEntity(entity));
        }
        return list;
    }

    @Override
    public Set<String> getScoreboardTags() {
        return bukkitEntity.getScoreboardTags();
    }

    @Override
    public void addScoreboardTag(String tag) {
        bukkitEntity.addScoreboardTag(tag);
    }

    @Override
    public boolean hasScoreboardTag(String tag) {
        return bukkitEntity.getScoreboardTags().contains(tag);
    }

    @Override
    public void setOnFireTicks(int ticks) {
        bukkitEntity.setFireTicks(ticks);
    }

    @Override
    public void addPassenger(EsEntity entity) {
        if (Main.minecraftVersion.getMinor() > 11) {
            bukkitEntity.addPassenger(((BukkitEntity) entity).getBukkitEntity());
        } else {
            //noinspection deprecation
            bukkitEntity.setPassenger(((BukkitEntity) entity).getBukkitEntity());
        }
    }
}
