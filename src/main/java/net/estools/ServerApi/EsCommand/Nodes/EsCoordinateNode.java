package net.estools.ServerApi.EsCommand.Nodes;

public class EsCoordinateNode extends EsArgumentNode {
    private EsCoordinateNode(String id) {
        super(id);
    }

    public static EsCoordinateNode coordinateArg(String id) {
        return new EsCoordinateNode(id);
    }
}
