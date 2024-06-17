package net.serble.estools.Commands;

import java.util.HashMap;
import java.util.UUID;

import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsTeleportCause;
import net.serble.estools.ServerApi.Events.EsPlayerDeathEvent;
import net.serble.estools.ServerApi.Events.EsPlayerTeleportEvent;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.ServerPlatform;

public class Back extends EsToolsCommand implements EsEventListener {
	private static final HashMap<UUID, EsLocation> prevLocations = new HashMap<>();

	@Override
	public void onEnable() {
		Main.registerEvents(this);
	}

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }

		if (Main.platform == ServerPlatform.Folia) {
			send(sender, "&cCurrently due to Folia issues /back only works for death locations");
		}
		
		EsPlayer p = (EsPlayer) sender;
		if (prevLocations.containsKey(p.getUniqueId())) {
			p.teleport(prevLocations.get(p.getUniqueId()));
			send(sender, "&aTeleported to last location!");
		} else {
			send(sender, "&cYou do not have a last location");
		}

		return true;
	}

	@Override
	public void executeEvent(EsEvent event) {
		if (event instanceof EsPlayerDeathEvent) {
			EsPlayerDeathEvent e = (EsPlayerDeathEvent) event;

			EsPlayer p = e.getPlayer();
			prevLocations.put(p.getUniqueId(), p.getLocation());
			return;
		}

		if (event instanceof EsPlayerTeleportEvent) {
			EsPlayerTeleportEvent e = (EsPlayerTeleportEvent) event;
			if (Main.minecraftVersion.getMinor() > 9 &&
					!equalsOr(e.getCause(), EsTeleportCause.Command, EsTeleportCause.Plugin)) {
				return;
			}
			prevLocations.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
		}
	}
}
