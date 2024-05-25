package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEquipmentSlot;
import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.Events.EsBlockPlaceEvent;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class Infinite extends EsToolsCommand implements EsEventListener {
    private static final ArrayList<UUID> currentPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        Main.registerEvents(this);
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        UUID pu = ((EsPlayer) sender).getUniqueId();

        if (!currentPlayers.contains(pu)) {
            currentPlayers.add(pu);
            send(sender, "&aYou now have &6infinite &ablocks!");
        } else {
            currentPlayers.remove(pu);
            send(sender, "&aYou now have &6finite &ablocks!");
        }

        return true;
    }

    @Override
    public void executeEvent(EsEvent event) {
        if (!(event instanceof EsBlockPlaceEvent)) {
            return;
        }
        EsBlockPlaceEvent e = (EsBlockPlaceEvent) event;

        if (!currentPlayers.contains(e.getPlayer().getUniqueId())) {
            return;
        }

        EsItemStack item = e.getPlacedItem().clone();
        EsEquipmentSlot slot = e.getHand();

        Main.server.runTask(() -> e.getPlayer().getInventory().setItem(slot, item));
    }
}
