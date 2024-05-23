package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsGameMode;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.ServerApi.Interfaces.EsWorld;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

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
    public EsItemStack getMainHand() {
        return new BukkitItemStack(bukkitGetMainHand());
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
    public EsInventory getInventory() {
        return new BukkitInventory(bukkitPlayer.getInventory());
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
    public EsWorld getWorld() {
        return new BukkitWorld(bukkitPlayer.getWorld());
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
    public Block getTargetBlock() {
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
