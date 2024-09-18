package net.estools.ServerApi.EsCommand.Nodes;

public class EsLongNode extends EsNumberArgument {
    private EsLongNode(String id, long min, long max) {
        super(id, min, max);
    }

    private EsLongNode(String id, MinMaxSupplier supplier) {
        super(id, supplier);
    }

    public static EsLongNode longArg(String id) {
        return new EsLongNode(id, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static EsLongNode longArg(String id, long min, long max) {
        return new EsLongNode(id, min, max);
    }

    public static EsLongNode longArg(String id, MinMaxSupplier supplier) {
        return new EsLongNode(id, supplier);
    }
}
