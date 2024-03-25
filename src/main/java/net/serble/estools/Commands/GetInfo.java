package net.serble.estools.Commands;

import net.serble.estools.Effects;
import net.serble.estools.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import net.serble.estools.EntityCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class GetInfo extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/getinfo <entity>"));
			return false;
		}

		Entity entity = getNonLivingEntity(sender, args[0]);
		
		if (entity == null)
			return false;

		Location loc = entity.getLocation();

		String name = getEntityName(entity);

		// Global Values
		String info =
				"&6&lEntity Info:\n" +
				"&aName: &6%s\n" +
				"&aType: &6%s\n" +
				"&aLocation: &6%s, %s, %s\n" +
				"&aWorld: &6%s\n" +
				"&aUUID: &6%s\n";
		info = String.format(info,
				name,
				entity.getType().name(),
				loc.getBlockX(),
				loc.getBlockY(),
				loc.getBlockZ(),
				loc.getWorld().getName(),
				entity.getUniqueId());

		// Passengers
		if (Main.version > 12) {
			StringBuilder passengerText = new StringBuilder("&aPassengers: &6");
			boolean passengersExist = false;
			for (Entity passenger : entity.getPassengers()) {
				passengerText.append(getEntityName(passenger)).append(" (").append(passenger.getType()).append("), ");
				passengersExist = true;
			}
			if (passengersExist) {
				passengerText.deleteCharAt(passengerText.length() - 1);
				passengerText.deleteCharAt(passengerText.length() - 1);
				info += passengerText + "\n";
			} else {
				info += "&aPassengers: &6None\n";
			}
		}

		// Living entity
		if (entity instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) entity;
			String maxHealth = String.valueOf(Math.round(getMaxHealth(le)));

			StringBuilder potionEffects = new StringBuilder();

			{
				Object[] pos = le.getActivePotionEffects().toArray();

				if (pos.length == 0) {
					potionEffects.append("None");
				} else {
					for (int i = 0; i < pos.length - 1; i++) {
						PotionEffect po = (PotionEffect) pos[i];
						potionEffects.append(Effects.getName(po.getType())).append(", ");
					}

					potionEffects.append(Effects.getName(((PotionEffect) pos[pos.length - 1]).getType()));
				}
			}

			String livingEntityInfo =
							"&aHealth: &6%s\n" +
							"&aMax Health: &6%s\n" +
							"&aPotion Effects: &6%s\n";
			livingEntityInfo = String.format(livingEntityInfo,
					getHealth(le),
					maxHealth,
					potionEffects);
			info += livingEntityInfo;

			if (Main.version > 12) {
				// Scoreboard tags
				StringBuilder tags = new StringBuilder("&aScoreboard Tags: &6");
				boolean tagsExist = false;
				for (String tag : le.getScoreboardTags()) {
					tags.append(tag).append(", ");
					tagsExist = true;
				}
				if (!tagsExist) {
					info += "&aScoreboard Tags: &6None\n";
				} else {
					tags.deleteCharAt(tags.length() - 1);
					tags.deleteCharAt(tags.length() - 1);
					info += tags + "\n";
				}
			}

		}

		if (entity instanceof Player) {
			// Players

			Player player = (Player) entity;

			String playerInfo =
							"&aHunger: &6%s\n" +
							"&aSaturation: &6%s\n" +
							"&aCan Fly: &6%s\n" +
							"&aIs Flying: &6%s\n";

			info += String.format(playerInfo,
					player.getFoodLevel(),
					player.getSaturation(),
					player.getAllowFlight(),
					player.isFlying());
		} else {
			// Non player entities

			String entityInfo = "";
			entityInfo = String.format(entityInfo);
			// info += entityInfo; // currently there is no extra info for non player entities
		}
		s(sender, info);
		return true;
	}

}

