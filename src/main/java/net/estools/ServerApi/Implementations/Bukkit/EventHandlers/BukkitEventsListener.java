package net.estools.ServerApi.Implementations.Bukkit.EventHandlers;

import net.estools.Main;
import net.estools.ServerApi.EsAction;
import net.estools.ServerApi.EsEquipmentSlot;
import net.estools.ServerApi.Events.*;
import net.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEnums1_1Plus;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEquipmentSlotHelper;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsBlock;
import net.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class BukkitEventsListener implements Listener, CommandExecutor {

    private double getDamageFromEvent(EntityDamageEvent e) {
        if (Main.minecraftVersion.getMinor() > 5) {
            return e.getDamage();
        }

        try {
            return (double)(int)EntityDamageEvent.class.getMethod("getDamage").invoke(e);
        } catch (Exception ex) {
            Bukkit.getLogger().severe(ex.toString());
            return 0d;
        }
    }

    private void setDamageFromEvent(EntityDamageEvent e, @SuppressWarnings("SameParameterValue") double d) {
        if (Main.minecraftVersion.getMinor() > 5) {
            e.setDamage(d);
            return;
        }

        try {
            //noinspection JavaReflectionMemberAccess, It's an int in older versions
            EntityDamageEvent.class.getMethod("setDamage", int.class).invoke(e, (int) d);
        } catch (Exception ex) {
            Bukkit.getLogger().severe(ex.toString());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        EsEquipmentSlot slot = EsEquipmentSlot.Hand;
        if (Main.minecraftVersion.getMinor() > 8) {
            slot = BukkitEquipmentSlotHelper.fromBukkitEquipmentSlot(e.getHand());
        }
        EsBlockPlaceEvent ee = new EsBlockPlaceEvent(new BukkitPlayer(e.getPlayer()), BukkitHelper.fromBukkitItem(e.getItemInHand()), slot);
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        EsPlayerTeleportEvent ee = new EsPlayerTeleportEvent(
                new BukkitPlayer(e.getPlayer()),
                BukkitEnums1_1Plus.fromBukkitTeleportCause(e.getCause()),
                BukkitHelper.fromBukkitLocation(Objects.requireNonNull(e.getTo())));
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        try {
            // In old versions getEntity() isn't in PlayerDeathEvent it is in EntityDeathEvent which
            // returns a LivingEntity, so we have to use reflection to get it and then cast it to a Player
            Player p = (Player) EntityEvent.class.getMethod("getEntity").invoke(e);

            EsPlayerDeathEvent ee = new EsPlayerDeathEvent(new BukkitPlayer(p));
            Main.callEvent(ee);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Main.logger.severe(ex.toString());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        EsEntityDamageEvent ee = new EsEntityDamageEvent(BukkitHelper.fromBukkitEntity(e.getEntity()), getDamageFromEvent(e));
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        setDamageFromEvent(e, ee.getDamage());
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Main.callEvent(new EsPlayerQuitEvent(new BukkitPlayer(e.getPlayer())));
    }

    @EventHandler
    public void onKick(PlayerQuitEvent e) {
        Main.callEvent(new EsPlayerQuitEvent(new BukkitPlayer(e.getPlayer())));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        EsBlock cb = BukkitHelper.fromBukkitBlock(e.getClickedBlock());
        EsPlayer p = new BukkitPlayer(e.getPlayer());
        EsAction ac = BukkitHelper.fromBukkitAction(e.getAction());
        EsPlayerInteractEvent ee = new EsPlayerInteractEvent(p, cb, ac);

        ee.setUseInteractedBlock(BukkitHelper.fromBukkitEventResult(e.useInteractedBlock()));
        ee.setUseItemInHand(BukkitHelper.fromBukkitEventResult(e.useItemInHand()));
        Main.callEvent(ee);
        e.setUseInteractedBlock(BukkitHelper.toBukkitEventResult(ee.getUseInteractedBlock()));
        e.setUseItemInHand(BukkitHelper.toBukkitEventResult(ee.getUseItemInHand()));
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        EsPlayer p = new BukkitPlayer(e.getPlayer());
        String[] lines = e.getLines();
        EsSignChangeEvent ee = new EsSignChangeEvent(p, lines);
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
        for (int i = 0; i < lines.length; i++) {
            e.setLine(i, ee.getLine(i));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Main.executeCommand(BukkitHelper.fromBukkitCommandSender(sender), command.getName(), args);
    }
}
