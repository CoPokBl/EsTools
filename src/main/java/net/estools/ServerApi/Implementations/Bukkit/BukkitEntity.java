package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.Main;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BukkitEntity implements EsEntity {
    private final Entity bukkitEntity;

    public BukkitEntity(Entity bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
    }

    public Entity getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public void teleport(EsLocation loc) {
        // This was moved from PaperLib.teleport() because the PaperLib class imports
        // things that don't exist in Bukkit 1.0 and therefore can't load.
        bukkitEntity.teleport(BukkitHelper.toBukkitLocation(loc));
    }

    @Override
    public String getType() {
        if (Main.minecraftVersion.isAtLeast(1, 1, 0)) {
            return bukkitEntity.getType().name();
        }

        // getType() doesn't exist
        return "ID " + bukkitEntity.getEntityId();
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
            // Cast isn't redundant because that method is only in LivingEntity in old versions
            @SuppressWarnings("RedundantCast") String name = ((LivingEntity)bukkitEntity).getCustomName();
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
        if (Main.minecraftVersion.isAtLeast(1, 2, 0)) {
            bukkitEntity.sendMessage(msg);
            return;
        }

        // The sendMessage(String[]) method doesn't exist
        // So combine the args into one String
        StringBuilder sb = new StringBuilder();
        for (String s : msg) {
            sb.append(s);
        }
        bukkitEntity.sendMessage(sb.toString());
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
            list.add(BukkitHelper.fromBukkitEntity(entity));
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

    @Override
    public void setFallDistance(float dis) {
        bukkitEntity.setFallDistance(dis);
    }
}
