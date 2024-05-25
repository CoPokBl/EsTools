package net.serble.estools.ServerApi;

import net.serble.estools.ServerApi.Interfaces.EsEvent;

public interface EsEventListener {
    void executeEvent(EsEvent event);
}
