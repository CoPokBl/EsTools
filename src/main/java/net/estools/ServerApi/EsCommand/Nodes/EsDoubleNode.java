package net.estools.ServerApi.EsCommand.Nodes;

public class EsDoubleNode extends EsArgumentNode {
    private EsDoubleNode(String id) {
        super(id);
    }

    public static EsDoubleNode floatArg(String id) {
        return new EsDoubleNode(id);
    }
}
