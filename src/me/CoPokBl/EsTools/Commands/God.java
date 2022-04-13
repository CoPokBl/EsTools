package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class God extends EntityCommand implements Listener {
	private static HashMap<UUID, BukkitRunnable> currentPlayers = new HashMap<>();

	private static final String usage = genUsage("/god <entity> <time>");

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.current);
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
					timer = (int)(Double.parseDouble(args[1]) * 20);
				} catch (Exception x) {
					s(sender, usage);
					return true;
				}
			}
		}

		UUID u = p.getUniqueId();

		BukkitRunnable r = null;

		String timerStr = "forever";
		if (timer >= 0) {
			timerStr = String.valueOf(timer / 20d) + " seconds";

			r = new BukkitRunnable() {
				@Override
				public void run() {
					currentPlayers.remove(u);
				}
			};

			r.runTaskLaterAsynchronously(Main.current, timer);
		}

		if (currentPlayers.containsKey(u)) {
			if (currentPlayers.get(u) != null) {
				currentPlayers.get(u).cancel();
			}

			currentPlayers.remove(u);
			s(sender, "&aGod mode &6disabled&a for &6%s", p.getName());
		}
		else {
			currentPlayers.put(u, r);
			s(sender, "&aGod mode &6enabled&a for &6%s&a for &6%s", p.getName(), timerStr);
		}
		return true;
	}

	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (currentPlayers.containsKey(e.getEntity().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}
