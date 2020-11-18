package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class Infinite extends CMD implements Listener {
    private static ArrayList<UUID> currentPlayers = new ArrayList<>();

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
            int amount = e.getItemInHand().getAmount();

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getItemInHand().setAmount(amount);
                    e.getPlayer().updateInventory();
                }
            }.runTask(Main.current);
        }
    }
}
