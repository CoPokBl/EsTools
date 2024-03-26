package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class EditSign extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		if (args.length < 2) {
			s(sender, genUsage("/editsign <line number> <line>"));
			return false;
		}
		
		Player p = (Player) sender;
		
		Block signB = getTargetBlock(p);

		if (signB == null || !(signB.getState() instanceof Sign)) {
			s(sender, "&cYou must be looking at a sign!");
			return false;
		}
		
		Sign sign = (Sign) signB.getState();
		
		int lineNum;
		
		try {
			lineNum = Integer.parseInt(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/editsign <line number> <line>"));
			return false;
		}
		
		if (lineNum < 1 || lineNum > 4) {
			s(sender, "&cline number must be between 1 and 4");
			return false;
		}
		
		StringBuilder lineText = new StringBuilder();
		
		for (int i = 1; i < args.length; i++) {
			lineText.append(args[i]).append(" ");
		}
		
		lineText = new StringBuilder(lineText.toString().trim());
		lineText = new StringBuilder(t(lineText.toString()));
		
		sign.setLine(lineNum - 1, lineText.toString());
		sign.update();
		
		s(sender, "&aAdded &6%s&a to line &6%d&a successfully!", lineText.toString(), lineNum);
		return true;
	}

	public Block getTargetBlock(Player p) {
		try {
			return (Block) LivingEntity.class.getMethod("getTargetBlock", HashSet.class, int.class).invoke(p, null, 5);
		}
		catch (Exception e) {
			Bukkit.getLogger().severe(e.toString());
			return null;
		}
	}
}
