package net.serble.estools.Commands;

import net.serble.estools.ConfigManager;
import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

public class God extends EntityCommand implements Listener {
	private static final HashMap<UUID, Integer> currentPlayers = new HashMap<>();

	private static final String usage = genUsage("/god <entity> <time>");

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.current);

		FileConfiguration f = ConfigManager.load("gods.yml");
		List<String> godList = f.getStringList("gods");
		godList.forEach(w -> currentPlayers.put(UUID.fromString(w), null));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		LivingEntity p;
		int timer = -1;
		
		if (args.length == 0) {
			if (isNotEntity(sender, usage))
				return false;
			
			p = (LivingEntity) sender;
		} else {
			p = getEntity(sender, args[0]);
			
			if (p == null)
				return false;

			if (args.length > 1) {
				try {
					timer = Math.max((int)(Double.parseDouble(args[1]) * 20), -1);
				} catch (NumberFormatException x) {
					s(sender, usage);
					return true;
				}
			}
		}

		UUID uid = p.getUniqueId();

		if (currentPlayers.containsKey(uid)) {
			int taskId = currentPlayers.get(uid);
			currentPlayers.remove(uid);

			if (taskId == -1) {
				save();
			} else {
				Bukkit.getScheduler().cancelTask(taskId);
			}

			s(sender, "&aGod mode &6disabled&a for &6%s", getEntityName(p));
		}
		else {
			Integer taskId = null;

			String timerStr = "forever";
			if (timer >= 0) {
				timerStr = timer / 20d + " seconds";

				taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.current, () -> currentPlayers.remove(uid), timer);
			}

			currentPlayers.put(uid, taskId);
			if (timer < 0) {
				save();
			}

			s(sender, "&aGod mode &6enabled&a for &6%s&a for &6%s", getEntityName(p), timerStr);
		}
		return true;
	}

	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (currentPlayers.containsKey(e.getEntity().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	private static void save() {
		FileConfiguration f = new YamlConfiguration();

		List<String> gods = new ArrayList<>();
		for (Map.Entry<UUID, Integer> kv : currentPlayers.entrySet()) {
			// only save if there isn't a time limit!
			if (kv.getValue() == null) {
				gods.add(kv.getKey().toString());
			}
		}

		f.set("gods", gods);

		ConfigManager.save("gods.yml", f);
	}
}
