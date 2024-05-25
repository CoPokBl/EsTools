package net.serble.estools.Commands;

import net.serble.estools.ConfigManager;
import net.serble.estools.EntityCommand;
import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.Events.EsEntityDamageEvent;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

// TODO: Event command needs migrating
public class God extends EntityCommand implements EsEventListener {
	private static final HashMap<UUID, Integer> currentPlayers = new HashMap<>();
	private static final String usage = genUsage("/god [entity] [time]");

	@Override
	public void onEnable() {
		Main.registerEvents(this);

		FileConfiguration f = ConfigManager.load("gods.yml");
		List<String> godList = f.getStringList("gods");
		godList.forEach(w -> currentPlayers.put(UUID.fromString(w), -1));
	}

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		EsLivingEntity p;
		int timer = -1;
		
		if (args.length == 0) {
			if (isNotEntity(sender, usage)) {
                return false;
            }
			
			p = (EsLivingEntity) sender;
		} else {
			p = getEntity(sender, args[0]);
			
			if (p == null) {
                return false;
            }

			if (args.length > 1) {
				try {
					timer = Math.max((int)(Double.parseDouble(args[1]) * 20), -1);
				} catch (NumberFormatException x) {
					send(sender, usage);
					return false;
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

			send(sender, "&aGod mode &6disabled&a for &6%s", p.getName());
		}
		else {
			int taskId = -1;

			String timerStr = "forever";
			if (timer >= 0) {
				timerStr = timer / 20d + " seconds";

				taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(EsToolsBukkit.plugin, () -> currentPlayers.remove(uid), timer);
			}

			currentPlayers.put(uid, taskId);
			if (timer < 0) {
				save();
			}

			send(sender, "&aGod mode &6enabled&a for &6%s&a for &6%s", p.getName(), timerStr);
		}

		return true;
	}

	private static void save() {
		FileConfiguration f = new YamlConfiguration();

		List<String> gods = new ArrayList<>();
		for (Map.Entry<UUID, Integer> kv : currentPlayers.entrySet()) {
			// only save if there isn't a time limit!
			if (kv.getValue() == -1) {
				gods.add(kv.getKey().toString());
			}
		}

		f.set("gods", gods);
		ConfigManager.save("gods.yml", f);
	}

	@Override
	public void executeEvent(EsEvent event) {
		if (!(event instanceof EsEntityDamageEvent)) {
			return;
		}
		EsEntityDamageEvent e = (EsEntityDamageEvent) event;

		if (currentPlayers.containsKey(e.getEntity().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}
