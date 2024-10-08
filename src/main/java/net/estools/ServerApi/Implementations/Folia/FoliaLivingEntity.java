package net.estools.ServerApi.Implementations.Folia;

import net.estools.Main;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.EsPotionEffectType;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEffectHelper;
import net.estools.ServerApi.Interfaces.EsLivingEntity;
import net.estools.ServerApi.Interfaces.EsWorld;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

// Can't extend BukkitEntity because it needs to extend FoliaEntity for it to work
public class FoliaLivingEntity extends FoliaEntity implements EsLivingEntity {
    private final org.bukkit.entity.LivingEntity bukkitEntity;

    public FoliaLivingEntity(org.bukkit.entity.LivingEntity entity) {
        super(entity);
        bukkitEntity = entity;
    }

    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
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

    @Override
    public void addPotionEffect(EsPotionEffect effect) {
        bukkitEntity.addPotionEffect(FoliaHelper.toBukkitPotionEffect(effect));
    }

    @Override
    public void removePotionEffect(EsPotionEffectType effect) {
        bukkitEntity.removePotionEffect(BukkitEffectHelper.toBukkitEffectType(effect));
    }

    @Override
    public List<EsPotionEffect> getActivePotionEffects() {
        Collection<PotionEffect> bukkitEffects = bukkitEntity.getActivePotionEffects();
        List<EsPotionEffect> out = new ArrayList<>(bukkitEffects.size());
        for (PotionEffect eff : bukkitEffects) {
            out.add(FoliaHelper.fromBukkitPotionEffect(eff));
        }

        return out;
    }

    @Override
    public EsWorld getWorld() {
        return new FoliaWorld(bukkitEntity.getWorld());
    }
}
