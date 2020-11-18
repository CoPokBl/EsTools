package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.UUID;

public class God extends EntityCommand implements Listener {
	private static ArrayList<UUID> currentPlayers = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		LivingEntity p;
		
		if (args.length == 0) {
			if (isNotEntity(sender, genUsage("/god <entity>")))
				return false;
			
			p = (LivingEntity) sender;
		} else {
			p = getEntity(sender, args[0]);
			
			if (p == null)
				return false;
		}

		UUID u = p.getUniqueId();

		if (currentPlayers.contains(u)) {
			currentPlayers.remove(u);
			s(sender, "&aGod mode &6disabled&a for &6%s", p.getName());
		}
		else {
			currentPlayers.add(u);
			s(sender, "&aGod mode &6enabled&a for &6%s", p.getName());
		}
		return true;
	}

	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (currentPlayers.contains(e.getEntity().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}
