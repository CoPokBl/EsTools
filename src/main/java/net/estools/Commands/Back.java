package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.EsEventListener;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsTeleportCause;
import net.estools.ServerApi.Events.EsPlayerDeathEvent;
import net.estools.ServerApi.Events.EsPlayerTeleportEvent;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.ServerPlatform;

import java.util.HashMap;
import java.util.UUID;

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
					!equalsOr(e.getCause(), EsTeleportCause.Command, EsTeleportCause.Plugin, EsTeleportCause.Unknown)) {
				return;
			}
			prevLocations.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
		}
	}
}
