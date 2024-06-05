package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsGameMode;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.EsSoundCategory;
import net.serble.estools.ServerApi.Interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;

public class BukkitPlayer extends BukkitLivingEntity implements EsPlayer {
    private final org.bukkit.entity.Player bukkitPlayer;

    public BukkitPlayer(org.bukkit.entity.Player entity) {
        super(entity);
        bukkitPlayer = entity;
    }

    public org.bukkit.entity.Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    @Override
    public int getFoodLevel() {
        return bukkitPlayer.getFoodLevel();
    }

    @Override
    public void setFoodLevel(int val) {
        bukkitPlayer.setFoodLevel(val);
    }

    @Override
    public double getSaturation() {
        return bukkitPlayer.getSaturation();
    }

    @Override
    public void setSaturation(double val) {
        bukkitPlayer.setSaturation((float) val);
    }

    @Override
    public EsItemStack getMainHand() {
        ItemStack mainHand = bukkitGetMainHand();
        if (mainHand == null) {
            return null;
        }
        return BukkitHelper.fromBukkitItem(mainHand);
    }

    @Override
    public void setMainHand(EsItemStack item) {
        setMainHandBukkit(bukkitPlayer, ((BukkitItemStack) item).getBukkitItem());
    }

    @Override
    public void openInventory(EsInventory inv) {
        bukkitPlayer.openInventory(((BukkitInventory) inv).getBukkitInventory());
    }

    @Override
    public void closeInventory() {
        bukkitPlayer.closeInventory();
    }

    @Override
    public EsPlayerInventory getInventory() {
        return new BukkitPlayerInventory(bukkitPlayer.getInventory());
    }

    @Override
    public void setFlySpeed(float val) {
        bukkitPlayer.setFlySpeed(val);
    }

    @Override
    public void setWalkSpeed(float val) {
        bukkitPlayer.setWalkSpeed(val);
    }

    @Override
    public void setGameMode(EsGameMode mode) {
        bukkitPlayer.setGameMode(BukkitHelper.toBukkitGameMode(mode));
    }

    @Override
    public EsGameMode getGameMode() {
        return BukkitHelper.fromBukkitGameMode(bukkitPlayer.getGameMode());
    }

    @Override
    public @Nullable EsBlock getTargetBlock() {
        return BukkitHelper.fromBukkitBlock(Objects.requireNonNull(bukkitGetTargetBlock()).getState());
    }

    @Override
    public boolean getAllowFlight() {
        return bukkitPlayer.getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean val) {
        bukkitPlayer.setAllowFlight(val);
    }

    @Override
    public boolean isFlying() {
        return bukkitPlayer.isFlying();
    }

    @Override
    public void playSound(String sound, EsSoundCategory category, EsLocation loc, int volume, int pitch) {
        bukkitPlayer.playSound(BukkitHelper.toBukkitLocation(loc), Sound.valueOf(sound), BukkitHelper.toBukkitSoundCategory(category), volume, pitch);
    }

    @SuppressWarnings("UnstableApiUsage")  // Yes I know it's a bug
    @Override
    public void updateInventory() {
        bukkitPlayer.updateInventory();
    }

    private Block bukkitGetTargetBlock() {
        if (Main.minecraftVersion.getMinor() > 12) {
            return bukkitPlayer.getTargetBlockExact(5);
        } else if (Main.minecraftVersion.getMinor() > 7) {
            return bukkitPlayer.getTargetBlock(null, 5);
        } else {
            try {
                //noinspection JavaReflectionMemberAccess
                return (Block) LivingEntity.class.getMethod("getTargetBlock", HashSet.class, int.class).invoke(bukkitPlayer, null, 5);
            }
            catch (Exception e) {
                Bukkit.getLogger().severe(e.toString());
                return null;
            }
        }
    }

    private org.bukkit.inventory.ItemStack bukkitGetMainHand() {
        if (Main.minecraftVersion.getMinor() > 8) {
            return bukkitPlayer.getInventory().getItemInMainHand();
        } else {
            //noinspection deprecation
            return bukkitPlayer.getInventory().getItemInHand();
        }
    }

    private void setMainHandBukkit(org.bukkit.entity.Player p, org.bukkit.inventory.ItemStack is) {
        if (Main.minecraftVersion.getMinor() > 8) {
            p.getInventory().setItemInMainHand(is);
        } else {
            //noinspection deprecation
            p.getInventory().setItemInHand(is);
        }
    }
}
