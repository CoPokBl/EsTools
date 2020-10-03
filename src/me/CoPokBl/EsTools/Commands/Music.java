package me.CoPokBl.EsTools.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class Music extends CMD {
	
	Sound[] musics = new Sound[] {
			Sound.MUSIC_DISC_BLOCKS, Sound.MUSIC_DISC_CAT, 
			Sound.MUSIC_DISC_CHIRP, Sound.MUSIC_DISC_FAR, 
			Sound.MUSIC_DISC_MALL, Sound.MUSIC_DISC_MELLOHI, 
			Sound.MUSIC_DISC_PIGSTEP, Sound.MUSIC_DISC_STAL,
			Sound.MUSIC_DISC_STRAD, Sound.MUSIC_DISC_WAIT,
			Sound.MUSIC_DISC_WARD 
		};
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "music"))
			return false;
		
		if (isNotPlayer(sender))
			return true;
		
		Player p = (Player) sender;
		
		Sound sound = null;
		
		if (args.length > 0) {
			try {
				sound = Sound.valueOf("MUSIC_DISC_" + args[0].toUpperCase());
			} catch (Exception e) {
				if (args[0].equalsIgnoreCase("random")) {
					sound = musics[(int)(Math.random() * musics.length)];
				} else {
					s(sender, genUsage("/music [song]"));
					return true;
				}
			}
		} else {
			sound = musics[(int)(Math.random() * musics.length)];
		}
		
		p.playSound(p.getLocation(), sound, SoundCategory.RECORDS, 100, 1);
		
		String name = sound.toString().toLowerCase().substring(11);
		name = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
		
		s(sender, "&aNow Playing: &6%s", name);
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tab = new ArrayList<String>();

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
