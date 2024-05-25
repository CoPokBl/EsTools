package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Events.EsBlockPlaceEvent;
import net.serble.estools.ServerApi.Events.EsEntityDamageEvent;
import net.serble.estools.ServerApi.Events.EsPlayerDeathEvent;
import net.serble.estools.ServerApi.Events.EsPlayerTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class BukkitEventsListener implements Listener {

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
        EsBlockPlaceEvent ee = new EsBlockPlaceEvent(new BukkitPlayer(e.getPlayer()), new BukkitItemStack(e.getItemInHand()), BukkitHelper.fromBukkitEquipmentSlot(e.getHand()));
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        EsPlayerTeleportEvent ee = new EsPlayerTeleportEvent(
                new BukkitPlayer(e.getPlayer()),
                BukkitHelper.fromBukkitTeleportCause(e.getCause()),
                BukkitHelper.fromBukkitLocation(Objects.requireNonNull(e.getTo())));
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
        Main.callEvent(ee);
        setDamageFromEvent(e, ee.getDamage());
        e.setCancelled(ee.isCancelled());
    }
}
