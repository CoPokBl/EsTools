package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.Main;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsOfflinePlayer;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.estools.ServerApi.Interfaces.EsLivingEntity;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetInfo extends EntityCommand {
	private static final String usage = genUsage("/getinfo <entity>");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}

		EsEntity entity = Main.server.getPlayer(args[0]);
		EsOfflinePlayer offlinePlayer = null;

		UUID validUuid = null;
		if (entity == null) {
			try {
				validUuid = UUID.fromString(args[0]);
				entity = Main.server.getEntity(validUuid);
			} catch (IllegalArgumentException ignored) { }
		}

		// If it's still null then lets assume it's an offline player
		if (entity == null || entity instanceof EsPlayer) {
            if (validUuid == null) {
				offlinePlayer = Main.server.getOfflinePlayer(args[0]);
			} else {
				offlinePlayer = Main.server.getOfflinePlayer(validUuid);
			}
        }

		// On old versions this is possible
		if (entity == null && offlinePlayer == null) {
			send(sender, "&cCould not find entity &6" + args[0]);
			return false;
		}

		// Global Values
		List<String> info = new ArrayList<>();
		info.add("&6&lEntity Info:");

		// Online entity
		if (entity != null) {
			EsLocation loc = entity.getLocation();

			info.add("&aName: &6" + entity.getName());
			info.add("&aType: &6" + entity.getType());
			info.add("&aLocation: &6" + worldLocationToString(loc));
			info.add("&aUUID: &6" + entity.getUniqueId());

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
					info.add(passengerText.toString());
				} else {
					info.add("&aPassengers: &6None");
				}
			}

			// ----------------------------------------------------------------------------
			// |                             Living Entities                              |
			// ----------------------------------------------------------------------------
			if (entity instanceof EsLivingEntity) {
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

				info.add("&aHealth: &6" + le.getHealth() + "/" + maxHealth);
				info.add("&aPotion Effects: &6" + potionEffects);

				if (Main.minecraftVersion.getMinor() > 12) {
					// Scoreboard tags
					StringBuilder tags = new StringBuilder("&aScoreboard Tags: &6");
					boolean tagsExist = false;
					for (String tag : le.getScoreboardTags()) {
						tags.append(tag).append(", ");
						tagsExist = true;
					}

					if (!tagsExist) {
						info.add("&aScoreboard Tags: &6None");
					} else {
						tags.deleteCharAt(tags.length() - 1);
						tags.deleteCharAt(tags.length() - 1);
						info.add(tags.toString());
					}
				}
			}

			// ----------------------------------------------------------------------------
			// |                              Online Players                              |
			// ----------------------------------------------------------------------------
			if (entity instanceof EsPlayer) {
				EsPlayer player = (EsPlayer) entity;

				info.add("&aHunger: &6" + player.getFoodLevel() + "/20");
				info.add("&aSaturation: &6" + player.getSaturation() + "/20");

				if (Main.minecraftVersion.getMinor() > 1) {
					info.add("&aCan Fly: &6" + player.getAllowFlight());
					info.add("&aIs Flying: &6" + player.isFlying());
				}
			}
		}

		// ----------------------------------------------------------------------------
		// |                                 Players                                  |
		// ----------------------------------------------------------------------------
		if (offlinePlayer != null) {
			if (entity == null) {  // Only things that are covered in the online players fields (They won't be there if player is offline)
				info.add("&aName: &6" + offlinePlayer.getName());
				info.add("&aUUID: &6" + offlinePlayer.getUuid());

				if (offlinePlayer.getLocation() != null) {
					info.add("&aLocation: &6" + worldLocationToString(offlinePlayer.getLocation()));
				}
			}

			info.add("&aOnline: &6" + offlinePlayer.isOnline());
			info.add("&aBanned: &6" + offlinePlayer.isBanned());
			info.add("&aPlayed Before: &6" + offlinePlayer.hasPlayedBefore());
			info.add("&aLast Played: &6" + Utils.stringifyTimestamp(offlinePlayer.getLastPlayed()));
			info.add("&aFirst Played: &6" + Utils.stringifyTimestamp(offlinePlayer.getFirstPlayed()));

			if (offlinePlayer.getLastDeathLocation() != null) {
				info.add("&aLast Death Location: &6" + worldLocationToString(offlinePlayer.getLastDeathLocation()));
			}

			if (offlinePlayer.getRespawnLocation() != null) {
				info.add("&aRespawn Location: &6" + worldLocationToString(offlinePlayer.getRespawnLocation()));
			}
		}

		String[] vals = info.toArray(new String[0]);
		send(sender, String.join("\n", vals));
		return true;
	}

}

