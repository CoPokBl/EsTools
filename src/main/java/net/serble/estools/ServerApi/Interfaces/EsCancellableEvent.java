package net.serble.estools.ServerApi.Interfaces;

public abstract class EsCancellableEvent implements EsEvent {
    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
