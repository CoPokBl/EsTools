package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.Main;
import net.estools.ServerApi.EsGameMode;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsSound;
import net.estools.ServerApi.EsSoundCategory;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

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
        Block target = bukkitGetTargetBlock();
        if (target == null) {
            return null;
        }
        return BukkitHelper.fromBukkitBlock(target.getState());
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
    public void playSound(EsSound sound, EsSoundCategory category, EsLocation loc, int volume, int pitch) {
        if (Main.minecraftVersion.getMinor() < 11) {
            bukkitPlayer.playSound(BukkitHelper.toBukkitLocation(loc), BukkitHelper.toBukkitSound(sound), volume, pitch);
            return;
        }
        bukkitPlayer.playSound(BukkitHelper.toBukkitLocation(loc), BukkitHelper.toBukkitSound(sound), BukkitHelper.toBukkitSoundCategory(category), volume, pitch);
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

    @Override
    public boolean hasPermission(String node) {
        return bukkitPlayer.hasPermission(node);
    }

    // I know that this overrides the BukkitEntity.sendMessage method
    // It has to because Entity.sendMessage() doesn't exist in old versions
    // so Player.sendMessage() needs to be used where possible.
    @Override
    public void sendMessage(String... args) {
        if (Main.minecraftVersion.isAtLeast(1, 2, 0)) {
            bukkitPlayer.sendMessage(args);
            return;
        }

        // The sendMessage(String[]) method doesn't exist
        // So combine the args into one String
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
        }
        bukkitPlayer.sendMessage(sb.toString());
    }

    @Override
    public boolean isPermissionSet(String node) {
        return bukkitPlayer.isPermissionSet(node);
    }
}
