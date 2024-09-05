package net.estools.ServerApi.EsCommand.Nodes;

import net.estools.ServerApi.EsCommand.EsCommandNode;

public class EsLiteralNode extends EsCommandNode {
    public final String label;

    private EsLiteralNode(String label) {
        this.label = label;
    }

    public static EsLiteralNode literal(String label) {
        return new EsLiteralNode(label);
    }
}
