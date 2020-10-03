package me.CoPokBl.EsTools.Commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class GM extends CMD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "tp"))
			return false;
		
		String ll = label.toLowerCase();
		
		if (ll.endsWith("gms"))
			setGamemode(sender, label, args, GameMode.SURVIVAL);

		else if (ll.endsWith("gmc"))
			setGamemode(sender, label, args, GameMode.CREATIVE);

		else if (ll.endsWith("gma"))
			setGamemode(sender, label, args, GameMode.ADVENTURE);

		else if (ll.endsWith("gmsp"))
			setGamemode(sender, label, args, GameMode.SPECTATOR);
		
		return true;
	}
	
	public void setGamemode(CommandSender sender, String label, String[] args, GameMode gm) {
		Player p;
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/%s [player]"), label))
				return;
			
			p = (Player) sender;
		} else {
			p = getPlayer(sender, args[0]);
			
			if (p == null)
				return;
			
			if (!p.getName().equals(sender.getName()))
				s(sender, "&aGamemode set to &6%s&a for &6%s", gm.toString(), p.getName());
		}

		p.setGameMode(gm);
		s(p, "&aGamemode set to &6%s", gm.toString());
	}
	
}
