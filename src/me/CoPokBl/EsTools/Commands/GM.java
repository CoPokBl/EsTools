package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.MultiPlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;

public class GM extends MultiPlayerCommand {
	
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
		ArrayList<Player> ps = new ArrayList<Player>();
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/%s [player]"), label))
				return;
			
			ps.add((Player) sender);
		} else {
			ps = getPlayers(sender, args);
			
			if (ps.isEmpty())
				return;

			s(sender, "&aGamemode set to &6%s&a for &6%s", gm.toString(), argsToString(args, 0));
		}

		Player s = null;

		if (sender instanceof Player) {
			s = (Player)sender;
		}

		for (Player p : ps) {
			p.setGameMode(gm);
			if (!s.equals(p))
				s(p, "&aGamemode set to &6%s", gm.toString());
		}
	}
}
