package net.serble.estools.Commands;

import java.util.HashMap;
import java.util.UUID;

import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.serble.estools.EsToolsCommand;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Back extends EsToolsCommand implements Listener {
	private static final HashMap<UUID, EsLocation> prevLocations = new HashMap<>();

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.bukkitPlugin);
	}

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		EsPlayer p = (EsPlayer) sender;
		if (prevLocations.containsKey(p.getUniqueId())) {
			p.teleport(prevLocations.get(p.getUniqueId()));
			send(sender, "&aTeleported to last location!");
		} else {
			send(sender, "&cYou do not have a last location");
		}

		return true;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (Main.minecraftVersion.getMinor() > 1) {
			prevLocations.put(e.getEntity().getUniqueId(), BukkitHelper.fromBukkitLocation(e.getEntity().getLocation()));
			return;
		}

		try {
			EsPlayer p = (EsPlayer)EntityEvent.class.getMethod("getEntity").invoke(e);
			prevLocations.put(p.getUniqueId(), p.getLocation());
		} catch (Exception ex) {
			Bukkit.getLogger().severe(ex.toString());
		}
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (equalsOr(e.getCause(), PlayerTeleportEvent.TeleportCause.COMMAND, PlayerTeleportEvent.TeleportCause.PLUGIN)) {
			prevLocations.put(e.getPlayer().getUniqueId(), BukkitHelper.fromBukkitLocation(e.getPlayer().getLocation()));
		}
	}
}
