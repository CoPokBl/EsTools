package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public abstract class EntityCommand extends CMD {
	
	public static LivingEntity getEntity(CommandSender sender, String name) {
		Entity entity = getNonLivingEntity(sender, name);
		if (entity instanceof LivingEntity) return (LivingEntity) entity;
		if (entity != null) {
			s(sender, "&cPlayer/Entity not found.");
		}
		return null;
	}

	public static Entity getNonLivingEntity(CommandSender sender, String name) {
		Entity p = Bukkit.getPlayer(name);

		if (p == null) {
			try {
				UUID uid = UUID.fromString(name);

				if (sender instanceof Player) {
					List<Entity> entities = ((Player)sender).getWorld().getEntities();

					for (Entity e : entities) {
						if (e.getUniqueId().equals(uid)) {
							p = e;
							break;
						}
					}
				}

				if (p != null)
					return p;
			} catch (Exception ignored) { }

			s(sender, "&cPlayer/Entity not found.");
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
