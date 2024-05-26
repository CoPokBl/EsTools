package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsSoundCategory;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Music extends EsToolsCommand {
	private static final String usage = genUsage("/music [song]");
	private static final List<String> musics = new ArrayList<>();
	private static final List<String> tabComplete = new ArrayList<>();
	private static final Random random = new Random();

	@Override
	public void onEnable() {
		if (Main.minecraftVersion.getMinor() > 12) {
			for (String s : Main.server.getSounds()) {
				if (s.startsWith("MUSIC_DISC")) {
					musics.add(s);
					tabComplete.add(s.toLowerCase().substring(11));
				}
			}

			musics.remove("MUSIC_DISC_13");
			musics.remove("MUSIC_DISC_11");
		} else {
			for (String s : Main.server.getSounds()) {
                if (s.startsWith("RECORD")) {
					tabComplete.add(s.toLowerCase().substring(7));

					if (!s.equals("RECORD_11") && !s.equals("RECORD_13")) {
						musics.add(s);
					}
				}
			}
		}

		tabComplete.add("random");
	}

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		EsPlayer p = (EsPlayer) sender;

		String sound;
		if (args.length > 0 && !args[0].equalsIgnoreCase("random")) {
			try {
				if (Main.minecraftVersion.getMinor() > 12) {
					sound = "MUSIC_DISC_" + args[0].toUpperCase();
				}
				else {
					sound = "RECORD_" + args[0].toUpperCase();
				}
			} catch (IllegalArgumentException e) {
				send(sender, usage);
				return false;
			}
		} else {
			sound = musics.get(random.nextInt(musics.size()));
		}

		if (Arrays.stream(Main.server.getSounds()).noneMatch(s -> s.equalsIgnoreCase(sound))) {
			send(sender, usage);
			return false;
		}

		p.playSound(sound, EsSoundCategory.Records, p.getLocation().add(0, 1000, 0), 100000, 1);

		// Substring away the MUSIC_DISK_ or the RECORD_ depending on the version
		// then reformat to be all lowercase except for the first character
		String name = sound.toLowerCase().substring(Main.minecraftVersion.getMinor() > 12 ? 11 : 7);
		name = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
		
		send(sender, "&aNow Playing: &6%s", name);
		return true;
	}
	
	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		if (args.length == 1) {
			return new ArrayList<>(tabComplete);
		}
		
		return new ArrayList<>();
	}
}
