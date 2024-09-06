package net.estools.ServerApi.EsCommand.Nodes;

public class EsIntegerNode extends EsArgumentNode {
    private EsIntegerNode(String id) {
        super(id);
    }

    public static EsIntegerNode integerArg(String id) {
        return new EsIntegerNode(id);
    }
}
