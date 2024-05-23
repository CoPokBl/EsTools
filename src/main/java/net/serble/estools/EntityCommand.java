package net.serble.estools;

import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEntity;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EntityCommand extends EsToolsCommand {
	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();
		
		if (sender instanceof EsPlayer) {
			EsPlayer p = (EsPlayer) sender;
			
			List<EsEntity> ens = p.getWorld().getNearbyEntities(p.getLocation(), 5, 5, 5);
			
			EsEntity en = getTarget(p, ens);
			
			if (en != null && !(en instanceof EsPlayer)) {
				String eu = en.getUniqueId().toString();
				tab.add(eu);
			}
		}
		
		for (EsPlayer p : Main.server.getOnlinePlayers()) {
			tab.add(p.getName());
		}
		
		return tab;
	}
	
	public static EsLivingEntity getEntity(EsCommandSender sender, String name) {
		EsEntity entity = getNonLivingEntity(sender, name);
		if (entity instanceof EsLivingEntity) return (EsLivingEntity) entity;
		if (entity != null) {
			send(sender, "&cPlayer/Entity not found.");
		}
		return null;
	}

	public static EsEntity getNonLivingEntity(EsCommandSender sender, String name) {
		EsEntity p = Main.server.getPlayer(name);

		if (p == null) {
			try {
				UUID uid = UUID.fromString(name);
				p = Main.server.getEntity(uid);

				if (p != null) {
                    return p;
                }
			} catch (IllegalArgumentException ignored) { }

			send(sender, "&cPlayer/Entity not found.");
		}

		return p;
	}
	
    public static <T extends EsEntity> T getTarget(final EsEntity entity,
												   final Iterable<T> entities) {
        if (entity == null)
            return null;
        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Position n = other.getLocation()
                    .subtract(entity.getLocation());
            if (entity.getLocation().getDirection().normalise().crossProduct(n)
                    .lengthSquared() < threshold
                    && n.normalise().dot(
                            entity.getLocation().getDirection().normalise()) >= 0) {
                if (target == null
                        || target.getLocation().distanceSquared(
                                entity.getLocation()) > other.getLocation()
                                .distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }
    
    public static boolean isNotEntity(EsCommandSender sender, String usage, Object... a) {
		if (!(sender instanceof EsLivingEntity)) {
			send(sender, usage, a);
			return true;
		}
		return false;
	}
	
	public static boolean isNotEntity(EsCommandSender sender) {
		if (!(sender instanceof EsLivingEntity)) {
			send(sender, "&cYou must be a player to run this command!");
			return true;
		}
		return false;
	}
}
