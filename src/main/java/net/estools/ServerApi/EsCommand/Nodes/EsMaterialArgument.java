package net.estools.ServerApi.EsCommand.Nodes;

public class EsMaterialArgument extends EsArgumentNode {
    protected EsMaterialArgument(String id) {
        super(id);
    }

    public static EsMaterialArgument materialArg(String id) {
        return new EsMaterialArgument(id);
    }
}
