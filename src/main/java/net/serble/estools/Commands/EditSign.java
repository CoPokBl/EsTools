package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.SignSide;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EditSign extends CMD {
	private static final String usage = genUsage("/editsign <line number> [line]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		if (args.length < 1) {
			s(sender, usage);
			return false;
		}
		
		Player p = (Player) sender;
		
		Block signB = getTargetBlock(p);

		if (signB == null || !(signB.getState() instanceof Sign)) {
			s(sender, "&cYou must be looking at a sign!");
			return false;
		}
		
		Sign sign = (Sign) signB.getState();

		switch (args[0].toLowerCase()) {
			case "glow":
				if (setGlow(sign, p, true)) {
					s(sender, "&aMade the sign &6glow!");
					return true;
				}

				return false;

			case "unglow":
				if (setGlow(sign, p, false)) {
					s(sender, "&aMade the sign no longer &6glow.");
					return true;
				}

				return false;
		}

		int lineNum;

		try {
			lineNum = Integer.parseInt(args[0]);
		} catch (Exception e) {
			s(sender, usage);
			return false;
		}
		
		if (lineNum < 1 || lineNum > 4) {
			s(sender, "&cline number must be between 1 and 4");
			return false;
		}
		
		StringBuilder lineTextBuilder = new StringBuilder();
		
		for (int i = 1; i < args.length; i++) {
			lineTextBuilder.append(args[i]).append(" ");
		}

		String lineText = t(lineTextBuilder.toString()).trim();

		if (Main.version >= 20) {
			SignSide side = sign.getTargetSide(p);
			side.setLine(lineNum - 1, lineText);
		}
		else {
			sign.setLine(lineNum - 1, lineText);
		}
		sign.update();

		if (lineText.isEmpty()) {
			s(sender, "&aEmptied line &6%d&a successfully!", lineNum);
			return true;
		}

		s(sender, "&aAdded &6%s&ato line &6%d&a successfully!", lineTextBuilder.toString(), lineNum);
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            tab.add("1");
            tab.add("2");
            tab.add("3");
            tab.add("4");

			if (Main.version >= 17) { // only add glow autocomplete if it exists
				tab.add("glow");
				tab.add("unglow");
			}
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

	public boolean setGlow(Sign sign, Player p, boolean glow) {
		if (Main.version >= 20) {
			SignSide side = sign.getTargetSide(p);

			if (side.isGlowingText() == glow) {
				if (glow) {
					s(p, "&cThis sign is already &6glowing!");
				}
				else {
					s(p, "&cThis sign is already not &6glowing!");
				}
				return false;
			}

			side.setGlowingText(glow);
		}
		else if (Main.version >= 17) {
			if (sign.isGlowingText() == glow) {
				if (glow) {
					s(p, "&cThis sign is already &6glowing!");
				}
				else {
					s(p, "&cThis sign is already not &6glowing!");
				}
				return false;
			}

			sign.setGlowingText(glow);
		}
		else {
			s(p, "&cGlowing signs do not exist in this version!");
			return false;
		}

		sign.update();
		return true;
	}
}
