package me.CoPokBl.EsTools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class EntityCommand extends CMD {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tab = new ArrayList<String>();
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			List<Entity> ens = p.getNearbyEntities(5, 5, 5);
			
			Entity en = getTarget(p, ens);
			
			if (en != null && !(en instanceof Player)) {
				String eu = en.getUniqueId().toString();
				tab.add(eu);
			}
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			tab.add(p.getName());
		}
		
		return tab;
	}
	
	public static LivingEntity getEntity(CommandSender sender, String name) {
		LivingEntity p = (LivingEntity)Bukkit.getPlayer(name);
		
		if (p == null) {
			try {
				p = (LivingEntity)Bukkit.getEntity(UUID.fromString(name));
			} catch (Exception e){
				s(sender, "&cPlayer/Entity not found.");
				return null;
			}
		}
			
		return p;
	}
	
    public static <T extends Entity> T getTarget(final Entity entity,
            final Iterable<T> entities) {
        if (entity == null)
            return null;
        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Vector n = other.getLocation().toVector()
                    .subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n)
                    .lengthSquared() < threshold
                    && n.normalize().dot(
                            entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null
                        || target.getLocation().distanceSquared(
                                entity.getLocation()) > other.getLocation()
                                .distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }
    
    public static boolean isNotEntity(CommandSender sender, String usage, Object... a) {
		if (!(sender instanceof LivingEntity)) {
			s(sender, usage, a);
			return true;
		}
		return false;
	}
	
	public static boolean isNotEntity(CommandSender sender) {
		if (!(sender instanceof LivingEntity)) {
			s(sender, "&cYou must be a player to run this command!");
			return true;
		}
		return false;
	}
}
