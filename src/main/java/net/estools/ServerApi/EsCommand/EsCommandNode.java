package net.estools.ServerApi.EsCommand;

import net.estools.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class EsCommandNode {
    private final List<EsCommandNode> children;
    private final List<EsCondition> conditions;
    private EsCommandExecutor executor = null;

    public EsCommandNode() {
        children = new ArrayList<>(1);
        conditions = new ArrayList<>(0);
    }

    public EsCommandNode then(EsCommandNode node) {
        children.add(node);
        return this;
    }

    public EsCommandNode withCondition(EsCondition condition) {
        conditions.add(condition);
        return this;
    }

    public EsCommandNode withCondition(Predicate<EsCommandContext> condition, EsCommandExecutor onFail) {
        conditions.add(new EsCondition(condition, onFail));
        return this;
    }

    public EsCommandNode execute(EsCommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public List<EsCommandNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public int getChildCount() {
        return children.size();
    }

    public boolean matchConditions(EsCommandContext context) {
        for (EsCondition condition : conditions) {
            if (!condition.condition().test(context)) {
                condition.executor().execute(context);
                return false;
            }
        }

        return true;
    }

    public void run(EsCommandContext context) {
        if (executor == null) {
            Main.logger.severe("Tried to execute command node with no executor, ignoring...");
            return;
        }

        executor.execute(context);
    }

    @FunctionalInterface
    public interface EsCommandExecutor {
        void execute(EsCommandContext context);
    }
}
