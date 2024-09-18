package net.estools.ServerApi.EsCommand.Nodes;

public class EsFloatNode extends EsNumberArgument {
    private EsFloatNode(String id, float min, float max) {
        super(id, min, max);
    }

    private EsFloatNode(String id, MinMaxSupplier supplier) {
        super(id, supplier);
    }

    public static EsFloatNode floatArg(String id) {
        return new EsFloatNode(id, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public static EsFloatNode floatArg(String id, float min, float max) {
        return new EsFloatNode(id, min, max);
    }

    public static EsFloatNode floatArg(String id, MinMaxSupplier supplier) {
        return new EsFloatNode(id, supplier);
    }
}
