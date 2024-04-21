package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Music extends EsToolsCommand {
	private static final String usage = genUsage("/music [song]");
	private static final List<Sound> musics = new ArrayList<>();
	private static final List<String> tabComplete = new ArrayList<>();
	private static final Random random = new Random();

	@Override
	public void onEnable() {
		if (Main.majorVersion > 12) {
			for (Sound s : Sound.values()) {
				if (s.name().startsWith("MUSIC_DISC")) {
					musics.add(s);
					tabComplete.add(s.name().toLowerCase().substring(11));
				}
			}

			musics.remove(Sound.MUSIC_DISC_13);
			musics.remove(Sound.MUSIC_DISC_11);
		} else {
			for (Sound s : Sound.values()) {
				String name = s.name();
				if (name.startsWith("RECORD")) {
					tabComplete.add(s.name().toLowerCase().substring(7));

					if (!name.equals("RECORD_11") && !name.equals("RECORD_13")) {
						musics.add(s);
					}
				}
			}
		}

		tabComplete.add("random");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		Player p = (Player) sender;

		Sound sound;
		if (args.length > 0 && !args[0].equalsIgnoreCase("random")) {
			try {
				if (Main.majorVersion > 12) {
					sound = Sound.valueOf("MUSIC_DISC_" + args[0].toUpperCase());
				}
				else {
					sound = Sound.valueOf("RECORD_" + args[0].toUpperCase());
				}
			} catch (IllegalArgumentException e) {
				send(sender, usage);
				return false;
			}
		} else {
			sound = musics.get(random.nextInt(musics.size()));
		}

		if (Main.majorVersion >= 11) {
			p.playSound(p.getLocation().add(0, 1000, 0), sound, SoundCategory.RECORDS, 100000, 1);
		}
		else {
			p.playSound(p.getLocation().add(0, 1000, 0), sound, 100000, 1);
		}

		// Substring away the MUSIC_DISK_ or the RECORD_ depending on the version
		// then reformat to be all lowercase except for the first character
		String name = sound.name().toLowerCase().substring(Main.majorVersion > 12 ? 11 : 7);
		name = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
		
		send(sender, "&aNow Playing: &6%s", name);
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		if (args.length == 1) {
			return tabComplete;
		}
		
		return new ArrayList<>();
	}
}
