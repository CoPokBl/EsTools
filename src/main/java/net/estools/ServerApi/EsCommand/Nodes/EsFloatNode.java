package net.estools.ServerApi.EsCommand.Nodes;

public class EsFloatNode extends EsArgumentNode {
    private EsFloatNode(String id) {
        super(id);
    }

    public static EsFloatNode doubleArg(String id) {
        return new EsFloatNode(id);
    }
}
