package net.estools.Commands;

import net.estools.ServerApi.EsCommand.EsCommandBuilder;
import net.estools.ServerApi.EsCommand.EsCommandContext;
import net.estools.EsToolsCommand;
import net.estools.ServerApi.EsCommand.EsCondition;
import net.estools.ServerApi.EsCommand.EsRelativePosition;
import net.estools.ServerApi.EsGameMode;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.Interfaces.EsWorld;
import net.estools.ServerApi.Position;

import static net.estools.ServerApi.EsCommand.Nodes.EsCoordinateNode.coordinateArg;
import static net.estools.ServerApi.EsCommand.Nodes.EsDoubleNode.doubleArg;
import static net.estools.ServerApi.EsCommand.Nodes.EsEnumArgument.enumArg;
import static net.estools.ServerApi.EsCommand.Nodes.EsIntegerNode.integerArg;
import static net.estools.ServerApi.EsCommand.Nodes.EsLiteralNode.literal;
import static net.estools.ServerApi.EsCommand.Nodes.EsStringNode.stringArg;
import static net.estools.ServerApi.EsCommand.Nodes.EsWordArgument.wordArg;

public class Sun extends EsToolsCommand {
    public void register() {
        EsCommandBuilder.create(
                literal("sun").withCondition(EsCondition.onlyPlayers())
                        .then(literal("soup").execute(context -> send(context.sender(), "Hello"))
                                .then(stringArg("epic").execute(this::execute)))
                        .then(literal("poup")
                                .then(doubleArg("cipe")
                                        .then(integerArg("asdf").execute(this::execute2))))
                        .then(literal("words")
                                .then(wordArg("words", "super", "epic", "gamer", "123").execute(this::execute3)))
                        .then(literal("enum")
                                .then(enumArg("enum", EsGameMode.class).execute(this::execute4)))
                        .then(literal("tp")
                                .then(coordinateArg("pos").execute(this::execute5)))
        ).register();
    }

    private void execute5(EsCommandContext context) {
        EsPlayer player = (EsPlayer) context.sender();
        Position pos = context.<EsRelativePosition>getArgument("pos").fromSender(player);

        player.teleport(new EsLocation(player.getWorld(), player.getLocation().getDirection(), pos));
    }

    private void execute(EsCommandContext context) {
        EsWorld world = ((EsPlayer) context.sender()).getWorld();
        world.setStorming(false);
        world.setThundering(false);

        send(context.sender(), "&aSet weather to &6clear" + context.<String>getArgument("epic"));
    }

    private void execute2(EsCommandContext context) {
        send(context.sender(), "N1: %f, N2: %d", context.<Double>getArgument("cipe"), context.<Integer>getArgument("asdf"));
    }

    private void execute3(EsCommandContext context) {
        send(context.sender(), "N1: %s", context.<String>getArgument("words"));
    }

    private void execute4(EsCommandContext context) {
        send(context.sender(), "N1: %s", context.<EsGameMode>getArgument("enum"));
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

