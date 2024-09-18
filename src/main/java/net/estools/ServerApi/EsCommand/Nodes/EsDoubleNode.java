package net.estools.ServerApi.EsCommand.Nodes;

public class EsDoubleNode extends EsNumberArgument {
    private EsDoubleNode(String id, double min, double max) {
        super(id, min, max);
    }

    private EsDoubleNode(String id, MinMaxSupplier supplier) {
        super(id, supplier);
    }

    public static EsDoubleNode doubleArg(String id) {
        return new EsDoubleNode(id, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static EsDoubleNode doubleArg(String id, double min, double max) {
        return new EsDoubleNode(id, min, max);
    }

    public static EsDoubleNode doubleArg(String id, MinMaxSupplier supplier) {
        return new EsDoubleNode(id, supplier);
    }
}
