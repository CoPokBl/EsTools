package net.estools.ServerApi.EsCommand.Nodes;

public class EsIntegerNode extends EsNumberArgument {
    private EsIntegerNode(String id, int min, int max) {
        super(id, min, max);
    }

    private EsIntegerNode(String id, MinMaxSupplier supplier) {
        super(id, supplier);
    }

    public static EsIntegerNode integerArg(String id) {
        return new EsIntegerNode(id, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static EsIntegerNode integerArg(String id, int min, int max) {
        return new EsIntegerNode(id, min, max);
    }

    public static EsIntegerNode integerArg(String id, MinMaxSupplier supplier) {
        return new EsIntegerNode(id, supplier);
    }
}
