package net.estools.ServerApi.EsCommand;

import net.estools.ServerApi.EsCommand.Nodes.EsLiteralNode;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class EsCommandManager {
    private final Map<Class<? extends EsCommandNode>, NodeRunnerExecDouble> nodeRunners;

    protected EsCommandManager() {
        nodeRunners = new HashMap<>();
    }

    protected <T extends EsCommandNode, O, B> void registerNodeRunner(Class<T> clazz, NodeRunnerExecutor<T, O> runner, NodeRunnerExecutor<T, B> tabber) {
        nodeRunners.put(clazz, new NodeRunnerExecDouble(runner, tabber));
    }

    public abstract void registerCommand(EsLiteralNode root);
    public abstract void start();

    protected <O> boolean processNodeTypes(EsCommandContext context, EsCommandNode node, boolean tab, O data) {
        for (Map.Entry<Class<? extends EsCommandNode>, NodeRunnerExecDouble> entry : nodeRunners.entrySet()) {
            if (entry.getKey().isInstance(node)) {
                if (tab) {
                    return entry.getValue().tabComplete.run(context, node, data);
                } else {
                    return entry.getValue().execute.run(context, node, data);
                }
            }
        }

        return false;
    }

    @FunctionalInterface
    protected interface NodeRunnerExecutor<T extends EsCommandNode, O> {
        boolean run(EsCommandContext context, T node, O data);
    }

    protected static class NodeRunnerExecDouble {
        public NodeRunnerExecutor execute;
        public NodeRunnerExecutor tabComplete;

        public NodeRunnerExecDouble(NodeRunnerExecutor execute, NodeRunnerExecutor tabComplete) {
            this.execute = execute;
            this.tabComplete = tabComplete;
        }
    }
}
