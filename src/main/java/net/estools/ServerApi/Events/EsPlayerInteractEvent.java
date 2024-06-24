package net.estools.ServerApi.Events;

import net.estools.ServerApi.EsAction;
import net.estools.ServerApi.EsEventResult;
import net.estools.ServerApi.Interfaces.EsBlock;
import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;
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
