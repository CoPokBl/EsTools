package net.estools.ServerApi.EsCommand;

import net.estools.Main;
import net.estools.ServerApi.EsCommand.Nodes.EsLiteralNode;

public class EsCommandBuilder {
    public EsLiteralNode root;

    // EsCommandBuilder.create(new EsLiteralNode("hello").then(new EsPlayerArgument("player"))).register();

    private EsCommandBuilder(EsLiteralNode root) {
        this.root = root;
    }

    public static EsCommandBuilder create(EsCommandNode root) {
        if (!(root instanceof EsLiteralNode)) {
            throw new IllegalArgumentException("Root node must be an EsLiteralNode");
        }

        return new EsCommandBuilder((EsLiteralNode) root);
    }

    public void register() {
        Main.server.getCommandManager().registerCommand(root);
    }
}
