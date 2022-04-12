package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.Effects;
import me.CoPokBl.EsTools.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class GetInfo extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/getinfo <entity>"));
			return false;
		}
		
		LivingEntity p = getEntity(sender, args[0]);
		
		if (p == null)
			return false;

		Location loc = p.getLocation();

		String maxHealth = String.valueOf(Math.round(getMaxHealth(p)));

		String name = "None";

		StringBuilder potionEffects = new StringBuilder();

		{
			Object[] pos = p.getActivePotionEffects().toArray();

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


		if (p instanceof Player pp) {
			name = pp.getName();

			s(sender, "&aName: &6%s\n&aHealth: &6%s\n&aMax Health: &6%s\n&aLocation: &6%s, %s, %s\n&aWorld: &6%s\n&aUUID: &6%s" +
							"\n&aPotion Effects: &6%s\n&aHunger: &6%s\n&aSaturation: &6%s\n&aCan Fly: &6%s\n&aIs Flying: &6%s",
					name, String.valueOf(p.getHealth()), maxHealth,
					loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), p.getUniqueId(),
					potionEffects.toString(), pp.getFoodLevel(), pp.getSaturation(), pp.getAllowFlight(), pp.isFlying()
			);
		} else {
			if (Main.version > 7) {
				name = p.getName();
			}

			s(sender, "&aName: &6%s\n&aHealth: &6%s\n&aMax Health: &6%s\n&aLocation: &6%s, %s, %s\n&aWorld: &6%s\n&aUUID: &6%s\n&aPotion Effects: &6%s",
					name, String.valueOf(p.getHealth()), maxHealth, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
					loc.getWorld().getName(), p.getUniqueId(), potionEffects.toString()
			);
		}
		return true;
	}

}

