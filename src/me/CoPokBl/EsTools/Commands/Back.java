package me.CoPokBl.EsTools.Commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Back extends CMD implements Listener {

	private static HashMap<UUID, Location> tpLoc = new HashMap<UUID, Location>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		Player p = (Player) sender;
		
		if (tpLoc.containsKey(p.getUniqueId())) {
			p.teleport(tpLoc.get(p.getUniqueId()));
			s(sender, "&aTeleported to last location!");
		} else {
			s(sender, "&cYou do not have a last location");
		}
		return true;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		tpLoc.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (equalsOr(e.getCause(), PlayerTeleportEvent.TeleportCause.COMMAND, PlayerTeleportEvent.TeleportCause.PLUGIN)) {
			tpLoc.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
		}
	}
}
