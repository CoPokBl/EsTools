package me.CoPokBl.EsTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class TP extends EntityCommand {	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "tp"))
			return false;
		
		LivingEntity p;
		
		String ll = label.toLowerCase();
		
		if (ll.endsWith("tphere")) {
			if (isNotEntity(sender))
				return false;
			
			if (args.length == 0) {
				s(sender, genUsage("/tphere <entity1> [entity2] [entity3]..."));
			}
			
			p = (LivingEntity) sender;
			
			for (String arg : args) {
				LivingEntity t = getEntity(sender, arg);
				
				if (t != null)
					t.teleport(p);
			}
		}

		else if (ll.endsWith("tpall")) {
			if (args.length == 0) {
				p = (LivingEntity) sender;
				
				if (isNotEntity(sender, genUsage("/tpall [entity]")))
					return false;
			} else {
				p = getEntity(sender, args[0]);
			}
			
			for (LivingEntity t : Bukkit.getOnlinePlayers()) {
				t.teleport(p);
			}
		}

		return false;
	}

}
