package net.estools.ServerApi.Implementations.Folia;

import net.estools.Main;
import net.estools.ServerApi.EsAction;
import net.estools.ServerApi.EsClickType;
import net.estools.ServerApi.EsInventoryAction;
import net.estools.ServerApi.Events.*;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEnums1_1Plus;
import net.estools.ServerApi.Interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;

public class FoliaEventsListener implements Listener, CommandExecutor {

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
        EsBlockPlaceEvent ee = new EsBlockPlaceEvent(new FoliaPlayer(e.getPlayer()), FoliaHelper.fromBukkitItem(e.getItemInHand()), FoliaHelper.fromBukkitEquipmentSlot(e.getHand()));
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        EsPlayerTeleportEvent ee = new EsPlayerTeleportEvent(
                new FoliaPlayer(e.getPlayer()),
                BukkitEnums1_1Plus.fromBukkitTeleportCause(e.getCause()),
                FoliaHelper.fromBukkitLocation(Objects.requireNonNull(e.getTo())));
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

            EsPlayerDeathEvent ee = new EsPlayerDeathEvent(new FoliaPlayer(p));
            Main.callEvent(ee);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Main.logger.severe(ex.toString());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        EsEntityDamageEvent ee = new EsEntityDamageEvent(FoliaHelper.fromBukkitEntity(e.getEntity()), getDamageFromEvent(e));
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        setDamageFromEvent(e, ee.getDamage());
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        EsInventory clInv = FoliaHelper.fromBukkitInventory(e.getClickedInventory());
        EsInventory inv = FoliaHelper.fromBukkitInventory(e.getInventory());
        EsClickType ct = FoliaHelper.fromBukkitClickType(e.getClick());
        EsInventoryAction ac = FoliaHelper.fromBukkitInventoryAction(e.getAction());
        EsItemStack ci = FoliaHelper.fromBukkitItem(e.getCurrentItem());
        EsPlayer cl = new FoliaPlayer((Player) e.getWhoClicked());
        EsItemStack cu = FoliaHelper.fromBukkitItem(e.getCursor());
        int sl = e.getSlot();
        EsInventoryClickEvent ee = new EsInventoryClickEvent(cl, sl, cu, inv, clInv, ci, ac, ct);
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Main.callEvent(new EsPlayerQuitEvent(new FoliaPlayer(e.getPlayer())));
    }

    @EventHandler
    public void onKick(PlayerQuitEvent e) {
        Main.callEvent(new EsPlayerQuitEvent(new FoliaPlayer(e.getPlayer())));
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        EsInventory inv = FoliaHelper.fromBukkitInventory(e.getInventory());
        EsPlayer pl = new FoliaPlayer((Player) e.getPlayer());
        EsInventoryCloseEvent ee = new EsInventoryCloseEvent(pl, inv);
        Main.callEvent(ee);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        EsInventory inv = FoliaHelper.fromBukkitInventory(e.getInventory());
        EsPlayer pl = new FoliaPlayer((Player) e.getWhoClicked());
        Set<Integer> cs = e.getRawSlots();
        EsInventoryView view = new FoliaInventoryView(e.getView());
        EsInventoryDragEvent ee = new EsInventoryDragEvent(pl, inv, cs, view);
        ee.setCancelled(e.isCancelled());
        Main.callEvent(ee);
        e.setCancelled(ee.isCancelled());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        EsBlock cb = FoliaHelper.fromBukkitBlock(e.getClickedBlock());
        EsPlayer p = new FoliaPlayer(e.getPlayer());
        EsAction ac = FoliaHelper.fromBukkitAction(e.getAction());
        EsPlayerInteractEvent ee = new EsPlayerInteractEvent(p, cb, ac);

        ee.setUseInteractedBlock(FoliaHelper.fromBukkitEventResult(e.useInteractedBlock()));
        ee.setUseItemInHand(FoliaHelper.fromBukkitEventResult(e.useItemInHand()));
        Main.callEvent(ee);
        e.setUseInteractedBlock(FoliaHelper.toBukkitEventResult(ee.getUseInteractedBlock()));
        e.setUseItemInHand(FoliaHelper.toBukkitEventResult(ee.getUseItemInHand()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Main.executeCommand(FoliaHelper.fromBukkitCommandSender(sender), command.getName(), args);
    }
}
