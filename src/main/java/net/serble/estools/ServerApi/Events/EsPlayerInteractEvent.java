package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.EsAction;
import net.serble.estools.ServerApi.Interfaces.EsBlock;
import net.serble.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.jetbrains.annotations.Nullable;

public class EsPlayerInteractEvent extends EsCancellableEvent {
    private final EsPlayer player;
    private final @Nullable EsBlock clickedBlock;
    private final EsAction action;

    public EsPlayerInteractEvent(EsPlayer player, @Nullable EsBlock clickedBlock, EsAction action) {
        this.player = player;
        this.clickedBlock = clickedBlock;
        this.action = action;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public EsAction getAction() {
        return action;
    }

    public @Nullable EsBlock getClickedBlock() {
        return clickedBlock;
    }
}