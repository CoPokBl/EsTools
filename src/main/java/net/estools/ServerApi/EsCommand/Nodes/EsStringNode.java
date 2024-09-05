package net.estools.ServerApi.EsCommand.Nodes;

public class EsStringNode extends EsArgumentNode {
    private EsStringNode(String id) {
        super(id);
    }

    public static EsStringNode stringArg(String id) {
        return new EsStringNode(id);
    }
}
