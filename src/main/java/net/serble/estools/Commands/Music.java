package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsSound;
import net.serble.estools.ServerApi.EsSoundCategory;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Music extends EsToolsCommand {
	private static final List<EsSound> randomDiscs = new ArrayList<>(); // discs that can be picked randomly
	private static final List<String> tabComplete = new ArrayList<>();
	private static final Random random = new Random();

	@Override
	public void onEnable() {
		for (EsSound s : Main.server.getSounds()) {
			if (s.getKey().startsWith("music_disc")) {
				randomDiscs.add(s);
				tabComplete.add(s.getKey().substring(11));
			}
		}

		randomDiscs.remove(EsSound.createUnchecked("music_disc.13"));
		randomDiscs.remove(EsSound.createUnchecked("music_disc.11"));
		randomDiscs.remove(EsSound.createUnchecked("music_disc.5"));

		tabComplete.add("random");
	}

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }

		EsPlayer p = (EsPlayer) sender;

		EsSound sound;
		if (args.length > 0 && !args[0].equalsIgnoreCase("random")) {
			sound = EsSound.fromKey("music_disc." + args[0]);

			if (sound == null) {
				send(sender, "&cThat music disc does not exist!");
				return false;
			}
		} else {
			sound = randomDiscs.get(random.nextInt(randomDiscs.size()));
		}

		// play very high up and with large volume so that the sound doesn't pan
		// volume doesn't make the sound louder, it just increases its range.
		p.playSound(sound, EsSoundCategory.Records, p.getLocation().add(0, 1000, 0), 100000, 1);

		// Substring away the music_disc, then reformat to capitalise the first letter
		String name = sound.getKey().toLowerCase().substring(11);
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
