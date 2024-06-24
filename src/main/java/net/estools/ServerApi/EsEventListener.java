package net.estools.ServerApi;

import net.estools.ServerApi.Interfaces.EsEvent;

public interface EsEventListener {
    void executeEvent(EsEvent event);
}
