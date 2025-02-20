package net.estools.ServerApi.Implementations.Folia;

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

// Can't extend BukkitEntity because it needs to extend FoliaLivingEntity for it to work
public class FoliaPlayer extends FoliaLivingEntity implements EsPlayer {
    private final org.bukkit.entity.Player bukkitPlayer;

    public FoliaPlayer(org.bukkit.entity.Player entity) {
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
        return FoliaHelper.fromBukkitItem(mainHand);
    }

    @Override
    public void setMainHand(EsItemStack item) {
        setMainHandBukkit(bukkitPlayer, ((FoliaItemStack) item).getBukkitItem());
    }

    @Override
    public void openInventory(EsInventory inv) {
        bukkitPlayer.openInventory(((FoliaInventory) inv).getBukkitInventory());
    }

    @Override
    public void closeInventory() {
        bukkitPlayer.closeInventory();
    }

    @Override
    public EsPlayerInventory getInventory() {
        return new FoliaPlayerInventory(bukkitPlayer.getInventory());
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
        bukkitPlayer.setGameMode(FoliaHelper.toBukkitGameMode(mode));
    }

    @Override
    public EsGameMode getGameMode() {
        return FoliaHelper.fromBukkitGameMode(bukkitPlayer.getGameMode());
    }

    @Override
    public @Nullable EsBlock getTargetBlock() {
        Block target = bukkitGetTargetBlock();
        if (target == null) {
            return null;
        }

        return FoliaHelper.fromBukkitBlock(target);
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
        bukkitPlayer.playSound(FoliaHelper.toBukkitLocation(loc), BukkitHelper.toBukkitSound(sound), FoliaHelper.toBukkitSoundCategory(category), volume, pitch);
    }

    @SuppressWarnings("UnstableApiUsage")  // Yes I know it's a bug
    @Override
    public void updateInventory() {
        bukkitPlayer.updateInventory();
    }

    @Override
    public EsInventory getEnderChest() {
        return FoliaHelper.fromBukkitInventory(bukkitPlayer.getEnderChest());
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
