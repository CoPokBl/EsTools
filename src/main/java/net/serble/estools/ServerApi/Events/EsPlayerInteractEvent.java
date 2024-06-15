package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.EsAction;
import net.serble.estools.ServerApi.EsEventResult;
import net.serble.estools.ServerApi.Interfaces.EsBlock;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.jetbrains.annotations.Nullable;

public class EsPlayerInteractEvent implements EsEvent {
    private final EsPlayer player;
    private final @Nullable EsBlock clickedBlock;
    private final EsAction action;
    private EsEventResult useInteractedBlock;
    private EsEventResult useItemInHand;

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

    public EsEventResult getUseItemInHand() {
        return useItemInHand;
    }

    public void setUseItemInHand(EsEventResult useItemInHand) {
        this.useItemInHand = useItemInHand;
    }

    public EsEventResult getUseInteractedBlock() {
        return useInteractedBlock;
    }

    public void setUseInteractedBlock(EsEventResult useInteractedBlock) {
        this.useInteractedBlock = useInteractedBlock;
    }
}
