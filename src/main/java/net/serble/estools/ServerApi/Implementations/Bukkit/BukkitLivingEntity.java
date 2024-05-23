package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;

import java.util.Objects;

public class BukkitLivingEntity extends BukkitEntity implements EsLivingEntity {
    private final org.bukkit.entity.LivingEntity bukkitEntity;

    public BukkitLivingEntity(org.bukkit.entity.LivingEntity entity) {
        super(entity);
        bukkitEntity = entity;
    }

    @Override
    public double getMaxHealth() {
        if (Main.minecraftVersion.getMinor() > 8) {
            return Objects.requireNonNull(bukkitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        } else {
            if (Main.minecraftVersion.getMinor() > 5) {
                //noinspection deprecation
                return bukkitEntity.getMaxHealth();
            }

            try {
                return (double)(int) org.bukkit.entity.LivingEntity.class.getMethod("getMaxHealth").invoke(bukkitEntity);
            } catch(Exception e) {
                Bukkit.getLogger().severe(e.toString());
                return 20d;
            }
        }
    }

    @Override
    public void setMaxHealth(double val) {
        if (Main.minecraftVersion.getMinor() > 8) {
            Objects.requireNonNull(bukkitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(val);
        } else {
            if (Main.minecraftVersion.getMinor() > 5) {
                //noinspection deprecation
                bukkitEntity.setMaxHealth(val);
                return;
            }

            try {
                //noinspection JavaReflectionMemberAccess
                org.bukkit.entity.LivingEntity.class.getMethod("setMaxHealth", int.class).invoke(bukkitEntity, (int)val);
            }
            catch (Exception ex) {
                Bukkit.getLogger().severe(ex.toString());
            }
        }
    }

    @Override
    public double getHealth() {
        if (Main.minecraftVersion.getMinor() > 5) {
            return bukkitEntity.getHealth();
        }

        try {
            return (double)(int) org.bukkit.entity.LivingEntity.class.getMethod("getHealth").invoke(bukkitEntity);
        }
        catch (Exception ex) {
            Bukkit.getLogger().severe(ex.toString());
            return 20d;
        }
    }

    @Override
    public void setHealth(double val) {
        if (Main.minecraftVersion.getMinor() > 5) {
            bukkitEntity.setHealth(val);
            return;
        }

        try {
            //noinspection JavaReflectionMemberAccess
            org.bukkit.entity.LivingEntity.class.getMethod("setHealth", int.class).invoke(bukkitEntity, (int)val);
        }
        catch (Exception ex) {
            Bukkit.getLogger().severe(ex.toString());
        }
    }
}
