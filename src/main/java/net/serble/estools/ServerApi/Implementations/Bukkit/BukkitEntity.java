package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.EsLocation;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.serble.estools.ServerApi.Implementations.Bukkit.BukkitHelper.toBukkitLocation;

public class BukkitEntity implements EsEntity {
    private final org.bukkit.entity.Entity bukkitEntity;

    public BukkitEntity(org.bukkit.entity.Entity bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public void teleport(EsLocation loc) {
        bukkitEntity.teleport(toBukkitLocation(loc));
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
}
