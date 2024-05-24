package net.serble.estools.Commands;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitItemStack;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.UUID;

// TODO: Events command, need migrating
public class Infinite extends EsToolsCommand implements Listener {
    private static final ArrayList<UUID> currentPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, EsToolsBukkit.plugin);
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        UUID pu = ((EsPlayer) sender).getUniqueId();

        if (!currentPlayers.contains(pu)) {
            currentPlayers.add(pu);
            send(sender, "&aYou now have &6infinite &ablocks!");
        } else {
            currentPlayers.remove(pu);
            send(sender, "&aYou now have &6finite &ablocks!");
        }

        return true;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (!currentPlayers.contains(e.getPlayer().getUniqueId())) {
            return;
        }

        EsItemStack item = new BukkitItemStack(e.getItemInHand().clone());
        EsPlayer p = new BukkitPlayer(e.getPlayer());

        // TODO: This is a terrible way of implementing this btw
        Main.server.runTaskLater(() -> {
            p.setMainHand(item);
            p.updateInventory();  // A bug makes this necessary
        }, 0);
    }
}
