package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class Infinite extends CMD implements Listener {
    private static final ArrayList<UUID> currentPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.current);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return true;

        UUID pu = ((Player)sender).getUniqueId();

        if (!currentPlayers.contains(pu)) {
            currentPlayers.add(pu);
            s(sender, "&aYou now have infinite blocks!");
        } else {
            currentPlayers.remove(pu);
            s(sender, "&aYou no longer have infinite blocks");
        }

        return true;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (currentPlayers.contains(e.getPlayer().getUniqueId())) {
            ItemStack item = e.getItemInHand().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    setMainHand(e.getPlayer(), item);
                    e.getPlayer().updateInventory();
                }
            }.runTask(Main.current);
        }
    }
}
