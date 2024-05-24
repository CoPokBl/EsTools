package net.serble.estools.Commands;

import net.serble.estools.ConfigManager;
import net.serble.estools.EntityCommand;
import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

public class Buddha extends EntityCommand implements Listener {
	private static final HashMap<UUID, Integer> currentPlayers = new HashMap<>();
	private static final String usage = genUsage("/buddha [entity] [time]");

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, EsToolsBukkit.plugin);

		FileConfiguration f = ConfigManager.load("gods.yml");
		List<String> buddhas = f.getStringList("buddhas");
		buddhas.forEach(w -> currentPlayers.put(UUID.fromString(w), -1));
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

			send(sender, "&aBuddha mode &6disabled&a for &6%s", p.getName());
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

			send(sender, "&aBuddha mode &6enabled&a for &6%s&a for &6%s", p.getName(), timerStr);
		}

		return true;
	}

	private double getDamageFromEvent(EntityDamageEvent e) {
		if (Main.minecraftVersion.getMinor() > 5) {
			return e.getDamage();
		}

		try {
			return (double)(int)EntityDamageEvent.class.getMethod("getDamage").invoke(e);
		} catch (Exception ex) {
			Bukkit.getLogger().severe(ex.toString());
			return 0d;
		}
	}

	private void setDamageFromEvent(EntityDamageEvent e, @SuppressWarnings("SameParameterValue") double d) {
		if (Main.minecraftVersion.getMinor() > 5) {
			e.setDamage(d);
			return;
		}

		try {
            //noinspection JavaReflectionMemberAccess, It's an int in older versions
            EntityDamageEvent.class.getMethod("setDamage", int.class).invoke(e, (int) d);
		} catch (Exception ex) {
			Bukkit.getLogger().severe(ex.toString());
		}
	}

	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof EsLivingEntity) || !currentPlayers.containsKey(e.getEntity().getUniqueId())) {
			return;
		}

		EsLivingEntity entity = (EsLivingEntity) e.getEntity();

		// Get all our vars since Minecraft broke everything in 1.6
		double damage = getDamageFromEvent(e);
		double health = entity.getHealth();
		double maxHealth = entity.getMaxHealth();

		if (damage < health) {  // Not lethal
			return;
		}

		// Lethal hit (lethal company reference)
		double extraDamage = damage - health;
		double resultingDamageTaken = extraDamage % maxHealth;

		setDamageFromEvent(e, 0);
		entity.setHealth(maxHealth - resultingDamageTaken);
	}

	private static void save() {
		FileConfiguration f = new YamlConfiguration();

		List<String> buddhas = new ArrayList<>();
		for (Map.Entry<UUID, Integer> kv : currentPlayers.entrySet()) {
			// only save if there isn't a time limit!
			if (kv.getValue() == -1) {
				buddhas.add(kv.getKey().toString());
			}
		}

		f.set("buddhas", buddhas);
		ConfigManager.save("gods.yml", f);
	}
}
