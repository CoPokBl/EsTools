package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.Main;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.estools.ServerApi.Interfaces.EsLivingEntity;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GetInfo extends EntityCommand {
	private static final String usage = genUsage("/getinfo <entity>");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}

		EsEntity entity = getNonLivingEntity(sender, args[0]);
		if (entity == null) {
            return false;
        }

		EsLocation loc = entity.getLocation();
		String name = entity.getName();

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
				entity.getType(),
				loc.getBlockX(),
				loc.getBlockY(),
				loc.getBlockZ(),
				Objects.requireNonNull(loc.getWorld()).getName(),
				entity.getUniqueId());

		// Passengers
		if (Main.minecraftVersion.getMinor() > 12) {
			StringBuilder passengerText = new StringBuilder("&aPassengers: &6");
			boolean passengersExist = false;
			for (EsEntity passenger : entity.getPassengers()) {
				passengerText.append(passenger.getName()).append(" (").append(passenger.getType()).append("), ");
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

		if (entity instanceof EsLivingEntity) {  // Living entity
			EsLivingEntity le = (EsLivingEntity) entity;
			String maxHealth = String.valueOf(Math.round(le.getMaxHealth()));

			StringBuilder potionEffects = new StringBuilder();
			{
				List<EsPotionEffect> potions = le.getActivePotionEffects();

				if (potions.isEmpty()) {
					potionEffects.append("None");
				} else {
					potionEffects.append(potions.stream()
							.map(pot -> String.format("%s at %s for %s seconds", pot.getType(), pot.getAmp()+1, pot.getDuration()/20))
							.collect(Collectors.joining(", ")));
				}
			}

			String livingEntityInfo =
							"&aHealth: &6%s\n" +
							"&aMax Health: &6%s\n" +
							"&aPotion Effects: &6%s\n";
			livingEntityInfo = String.format(livingEntityInfo,
					le.getHealth(),
					maxHealth,
					potionEffects);

			info += livingEntityInfo;

			if (Main.minecraftVersion.getMinor() > 12) {
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

		if (entity instanceof EsPlayer) {  // Players
			EsPlayer player = (EsPlayer) entity;

			String playerInfo =
							"&aHunger: &6%s\n" +
							"&aSaturation: &6%s\n";

			playerInfo = String.format(playerInfo,
					player.getFoodLevel(),
					player.getSaturation());

			if (Main.minecraftVersion.getMinor() > 1) {
				playerInfo += "&aCan Fly: &6%s\n" +
						      "&aIs Flying: &6%s\n";

				playerInfo = String.format(playerInfo,
						player.getAllowFlight(),
						player.isFlying());
			}

			info += playerInfo;
		}

		send(sender, info);
		return true;
	}

}
