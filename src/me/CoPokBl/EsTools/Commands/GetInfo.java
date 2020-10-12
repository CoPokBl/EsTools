package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.Main;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class GetInfo extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/getinfo <entity>"));
			return false;
		}
		
		LivingEntity p = getEntity(sender, args[0]);
		
		if (p == null) 
			return false;

		Location loc = p.getLocation();

		String maxHealth = String.valueOf(Math.round(getMaxHealth(p)));

		s(sender, "&aName: &6%s\n&aHealth: &6%s\n&aMax Health: &6%s\n&aLocation: &6%s, %s, %s\n&aUUID: &6%s",
				p.getName(), String.valueOf(p.getHealth()), maxHealth,
				loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), p.getUniqueId());
		return true;
	}

}

