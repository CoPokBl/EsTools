package net.estools.ServerApi.EsCommand.Nodes;

import net.estools.ServerApi.EsCommand.EsCommandNode;

public abstract class EsArgumentNode extends EsCommandNode {
    private final String id;

    protected EsArgumentNode(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
