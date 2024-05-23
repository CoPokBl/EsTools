package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.SignSide;

import java.util.ArrayList;
import java.util.List;

// TODO: Command incomplete
public class EditSign extends EsToolsCommand {
	private static final String usage = genUsage("/editsign <line number> [line]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		if (args.length < 1) {
			send(sender, usage);
			return false;
		}
		
		EsPlayer p = (EsPlayer) sender;
		
		Block signB = p.getTargetBlock();

		if (signB == null || !(signB.getState() instanceof Sign)) {
			send(sender, "&cYou must be looking at a sign!");
			return false;
		}
		
		Sign sign = (Sign) signB.getState();

		switch (args[0].toLowerCase()) {
			case "glow":
				if (setGlow(sign, p, true)) {
					send(sender, "&aMade the sign &6glow!");
					return true;
				}

				return false;

			case "unglow":
				if (setGlow(sign, p, false)) {
					send(sender, "&aMade the sign no longer &6glow.");
					return true;
				}

				return false;
		}

		int lineNum;
		try {
			lineNum = Integer.parseInt(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}
		
		if (lineNum < 1 || lineNum > 4) {
			send(sender, "&cline number must be between 1 and 4");
			return false;
		}
		
		StringBuilder lineTextBuilder = new StringBuilder();
		
		for (int i = 1; i < args.length; i++) {
			lineTextBuilder.append(args[i]).append(" ");
		}

		String lineText = translate(lineTextBuilder.toString()).trim();

		if (Main.minecraftVersion.getMinor() >= 20) {
			SignSide side = sign.getTargetSide(p);
			side.setLine(lineNum - 1, lineText);
		} else {
            //noinspection deprecation
            sign.setLine(lineNum - 1, lineText);
		}
		sign.update();

		if (lineText.isEmpty()) {
			send(sender, "&aEmptied line &6%d&a successfully!", lineNum);
			return true;
		}

		send(sender, "&aAdded &6%s&ato line &6%d&a successfully!", lineTextBuilder.toString(), lineNum);
		return true;
	}

	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            tab.add("1");
            tab.add("2");
            tab.add("3");
            tab.add("4");

			if (Main.minecraftVersion.getMinor() >= 17) { // only add glow autocomplete if it exists
				tab.add("glow");
				tab.add("unglow");
			}
        }

		return tab;
	}

	@SuppressWarnings("deprecation")
    public boolean setGlow(Sign sign, Player p, boolean glow) {
		if (Main.majorVersion >= 20) {
			SignSide side = sign.getTargetSide(p);

			if (side.isGlowingText() == glow) {
				if (glow) {
					send(p, "&cThis sign is already &6glowing!");
				}
				else {
					send(p, "&cThis sign is already not &6glowing!");
				}
				return false;
			}

			side.setGlowingText(glow);
		}
		else if (Main.majorVersion >= 17) {
			if (sign.isGlowingText() == glow) {
				if (glow) {
					send(p, "&cThis sign is already &6glowing!");
				}
				else {
					send(p, "&cThis sign is already not &6glowing!");
				}
				return false;
			}

			sign.setGlowingText(glow);
		}
		else {
			send(p, "&cGlowing signs do not exist in this version!");
			return false;
		}

		sign.update();
		return true;
	}
}
