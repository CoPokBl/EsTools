package me.CoPokBl.EsTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class TP extends CMD implements CommandExecutor {	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "tp"))
			return false;
		
		Player p;
		
		String ll = label.toLowerCase();
		
		if (ll.endsWith("tphere")) {
			if (isNotPlayer(sender))
				return false;
			
			if (args.length == 0) {
				s(sender, genUsage("/tphere <Player1> [Player2] [Player3]..."));
			}
			
			p = (Player) sender;
			
			for (String arg : args) {
				Player t = getPlayer(sender, arg);
				
				if (t != null)
					t.teleport(p);
			}
		}

		else if (ll.endsWith("tpall")) {
			if (args.length == 0) {
				p = (Player) sender;
				
				if (isNotPlayer(sender, genUsage("/tpall [Player]")))
					return false;
			} else {
				p = getPlayer(sender, args[0]);
			}
			
			for (Player t : Bukkit.getOnlinePlayers()) {
				t.teleport(p);
			}
		}

		return false;
	}

}
