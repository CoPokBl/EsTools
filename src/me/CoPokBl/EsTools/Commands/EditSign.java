package me.CoPokBl.EsTools.Commands;

import java.util.ArrayList;
import java.util.List;

import me.CoPokBl.EsTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class EditSign extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "editsign"))
			return false;
		
		if (isNotPlayer(sender))
			return false;
		
		if (args.length < 2) {
			s(sender, genUsage("/editsign <line number> <line>"));
			return false;
		}
		
		Player p = (Player) sender;
		
		Block signB = p.getTargetBlockExact(5);

		if (signB == null || !(signB.getState() instanceof Sign)) {
			s(sender, "&cYou must be looking at a sign!");
			return false;
		}
		
		Sign sign = (Sign) signB.getState();
		
		int lineNum;
		
		try {
			lineNum = Integer.valueOf(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/editsign <line number> <line>"));
			return false;
		}
		
		if (lineNum < 1 || lineNum > 4) {
			s(sender, "&cline number must be between 1 and 4");
			return false;
		}
		
		String lineText = "";
		
		for (int i = 1; i < args.length; i++) {
			lineText += args[i] + " ";
		}
		
		lineText = lineText.trim();
		lineText = ChatColor.translateAlternateColorCodes('&', lineText);
		
		sign.setLine(lineNum - 1, lineText);
		sign.update();
		
		s(sender, "&aAdded &6%s&a to line &6%d&a successfully!", lineText, lineNum);
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<String>();

		switch (args.length) {
			case 1:
				tab.add("1");
				tab.add("2");
				tab.add("3");
				tab.add("4");
				break;
		}

		return tab;
	}
}
