package net.estools.ServerApi.EsCommand.Nodes;

public class EsLongNode extends EsArgumentNode {
    private EsLongNode(String id) {
        super(id);
    }

    public static EsLongNode longArg(String id) {
        return new EsLongNode(id);
    }
}
