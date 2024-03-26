package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Objects;

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
				Objects.requireNonNull(loc.getWorld()).getName(),
				entity.getUniqueId());

		// Living entity
		if (entity instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) entity;

			String livingEntityInfo =
							"&aHealth: &6%s\n";
			livingEntityInfo = String.format(livingEntityInfo,
					getHealth(le));
			info += livingEntityInfo;

		}

		if (entity instanceof Player) {
			// Players

			Player player = (Player) entity;

			String playerInfo =
							"&aHunger: &6%s\n" +
							"&aSaturation: &6%s\n";

			playerInfo = String.format(playerInfo,
					player.getFoodLevel(),
					player.getSaturation());

			info += playerInfo;
		}
		s(sender, info);
		return true;
	}

}

