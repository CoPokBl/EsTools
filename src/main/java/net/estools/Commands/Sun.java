package net.estools.Commands;

import net.estools.ServerApi.EsCommand.EsCommandBuilder;
import net.estools.ServerApi.EsCommand.EsCommandContext;
import net.estools.EsToolsCommand;
import net.estools.ServerApi.EsCommand.EsCondition;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.Interfaces.EsWorld;

import static net.estools.ServerApi.EsCommand.Nodes.EsLiteralNode.literal;
import static net.estools.ServerApi.EsCommand.Nodes.EsStringNode.stringArg;

public class Sun extends EsToolsCommand {
    public void register() {
        EsCommandBuilder.create(
                literal("sun").withCondition(EsCondition.onlyPlayers())
                        .then(literal("soup").execute(context -> send(context.sender(), "Hello"))
                                .then(stringArg("epic").execute(this::execute)))
                        .then(literal("poup")
                                .then(literal("cipe")))
        ).register();
    }

    private void execute(EsCommandContext context) {
        EsWorld world = ((EsPlayer) context.sender()).getWorld();
        world.setStorming(false);
        world.setThundering(false);

        send(context.sender(), "&aSet weather to &6clear" + context.<String>getArgument("epic"));
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsWorld world = ((EsPlayer) sender).getWorld();
        world.setStorming(false);
        world.setThundering(false);

        send(sender, "&aSet weather to &6clear");
        return true;
    }
}

