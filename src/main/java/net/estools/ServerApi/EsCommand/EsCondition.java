package net.estools.ServerApi.EsCommand;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.function.Predicate;

public class EsCondition {
    private final Predicate<EsCommandContext> condition;
    private final EsCommandNode.EsCommandExecutor executor;

    public EsCondition(Predicate<EsCommandContext> condition, EsCommandNode.EsCommandExecutor executor) {
        this.condition = condition;
        this.executor = executor;
    }

    public Predicate<EsCommandContext> condition() {
        return condition;
    }

    public EsCommandNode.EsCommandExecutor executor() {
        return executor;
    }

    public static EsCondition onlyPlayers() {
        return new EsCondition(context -> context.sender() instanceof EsPlayer, context -> EsToolsCommand.send(context.sender(), "&cYou must be a player to run this command!"));
    }
}
