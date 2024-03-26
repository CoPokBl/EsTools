package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();

		switch (args.length) {
			case 1:
				tab.add("1"); tab.add("2"); tab.add("3"); tab.add("4");
				break;
		}

		return tab;
	}

	public Block getTargetBlock(Player p) {
		if (Main.version > 12) {
			return p.getTargetBlockExact(5);
		} else if (Main.version > 7) {
			return p.getTargetBlock(null, 5);
		} else {
			try {
				return (Block) LivingEntity.class.getMethod("getTargetBlock", HashSet.class, int.class).invoke(p, null, 5);
			}
			catch (Exception e) {
				Bukkit.getLogger().severe(e.toString());
				return null;
			}
		}
	}
}
