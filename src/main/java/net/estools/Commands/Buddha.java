package net.estools.Commands;

import net.estools.Config.ConfigManager;
import net.estools.EntityCommand;
import net.estools.Main;
import net.estools.ServerApi.EsEventListener;
import net.estools.ServerApi.Events.EsEntityDamageEvent;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsLivingEntity;

import java.util.*;

public class Buddha extends EntityCommand implements EsEventListener {
	private static final HashMap<UUID, Integer> currentPlayers = new HashMap<>();
	private static final String usage = genUsage("/buddha [entity] [time]");
	private static final String configFile = "buddhas.yml";

	@SuppressWarnings("unchecked")  // I'm right, trust me
    @Override
	public void onEnable() {
		Main.registerEvents(this);

		List<String> f = (List<String>) ConfigManager.load(configFile, ArrayList.class);
		f.forEach(w -> currentPlayers.put(UUID.fromString(w), -1));
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
				Main.server.cancelTask(taskId);
			}

			send(sender, "&aBuddha mode &6disabled&a for &6%s", p.getName());
		}
		else {
			int taskId = -1;

			String timerStr = "forever";
			if (timer >= 0) {
				timerStr = timer / 20d + " seconds";

				taskId = Main.server.runTaskLater(() -> currentPlayers.remove(uid), timer);
			}

			currentPlayers.put(uid, taskId);
			if (timer < 0) {
				save();
			}

			send(sender, "&aBuddha mode &6enabled&a for &6%s&a for &6%s", p.getName(), timerStr);
		}

		return true;
	}

	private static void save() {
		List<String> buddhas = new ArrayList<>();
		for (Map.Entry<UUID, Integer> kv : currentPlayers.entrySet()) {
			// only save if there isn't a time limit!
			if (kv.getValue() == -1) {
				buddhas.add(kv.getKey().toString());
			}
		}

		ConfigManager.save(configFile, buddhas);
	}

	@Override
	public void executeEvent(EsEvent event) {
		if (!(event instanceof EsEntityDamageEvent)) {
			return;
		}
		EsEntityDamageEvent e = (EsEntityDamageEvent) event;

		if (!(e.getEntity() instanceof EsLivingEntity) || !currentPlayers.containsKey(e.getEntity().getUniqueId())) {
			return;
		}
		EsLivingEntity entity = (EsLivingEntity) e.getEntity();

		// Get all our vars since Minecraft broke everything in 1.6
		double damage = e.getDamage();
		double health = entity.getHealth();
		double maxHealth = entity.getMaxHealth();

		if (damage < health) {  // Not lethal
			return;
		}

		// Lethal hit (lethal company reference)
		double extraDamage = damage - health;
		double resultingDamageTaken = extraDamage % maxHealth;

		e.setDamage(0);
		entity.setHealth(maxHealth - resultingDamageTaken);
	}
}
