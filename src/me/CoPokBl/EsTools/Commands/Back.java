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

public class Back extends CMD implements Listener {

	private static HashMap<UUID, Location> deathLoc = new HashMap<UUID, Location>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "back"))
			return false;
		
		if (isNotPlayer(sender))
			return false;
		
		Player p = (Player) sender;
		
		if (deathLoc.containsKey(p.getUniqueId())) {
			p.teleport(deathLoc.get(p.getUniqueId()));
			s(sender, "&aTeleported to last death location!");
		} else {
			s(sender, "&4You do not have a last death location");
		}
		return true;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		deathLoc.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
	}
}
