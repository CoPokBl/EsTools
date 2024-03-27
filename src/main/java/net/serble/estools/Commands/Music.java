package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Music extends CMD {
	private static final String usage = genUsage("/music [song]");
	
	public static ArrayList<Sound> musics = new ArrayList<>();

	@Override
	public void onEnable() {
		for (Sound s : Sound.values()) {
			if (s.toString().startsWith("MUSIC_DISC")) {
				musics.add(s);
			}
		}

		musics.remove(Sound.MUSIC_DISC_13);
		musics.remove(Sound.MUSIC_DISC_11);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return true;
		
		Player p = (Player) sender;
		
		Sound sound;
		
		if (args.length > 0) {
			try {
				sound = Sound.valueOf("MUSIC_DISC_" + args[0].toUpperCase());
			} catch (Exception e) {
				if (args[0].equalsIgnoreCase("random")) {
					sound = musics.get((int)(Math.random() * musics.size()));
				} else {
					s(sender, usage);
					return true;
				}
			}
		} else {
			sound = musics.get((int)(Math.random() * musics.size()));
		}
		
		p.playSound(p.getLocation().add(0, 1000, 0), sound, SoundCategory.RECORDS, 100000, 1);
		
		String name = sound.toString().toLowerCase().substring(11);
		name = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
		
		s(sender, "&aNow Playing: &6%s", name);
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();

		if (args.length == 1) {
			for (Sound mat : Sound.values()) {
				String name = mat.toString();

				if (name.startsWith("MUSIC_DISC")) {
					tab.add(name.toLowerCase().substring(11));
				}
				
				tab.add("random");
			}
		}
		
		return tab;
	}
}
