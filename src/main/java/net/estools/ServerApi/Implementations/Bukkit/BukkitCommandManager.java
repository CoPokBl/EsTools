package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.EsCommand.EsCommandContext;
import net.estools.ServerApi.EsCommand.EsCommandManager;
import net.estools.ServerApi.EsCommand.EsCommandNode;
import net.estools.ServerApi.EsCommand.EsRelativePosition;
import net.estools.ServerApi.EsCommand.Nodes.*;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BukkitCommandManager extends EsCommandManager {
    private final List<EsLiteralNode> tree;

    public BukkitCommandManager() {
        tree = new ArrayList<>();
    }

    @Override
    public void start() {
        registerNodeRunner(EsLiteralNode.class, this::processLiteral, this::tabLiteral);
        registerNodeRunner(EsStringNode.class, this::processString, this::tabString);
        registerNumberRunner(EsDoubleNode.class, Double::parseDouble);
        registerNumberRunner(EsFloatNode.class, Float::parseFloat);
        registerNumberRunner(EsIntegerNode.class, Integer::parseInt);
        registerNumberRunner(EsLongNode.class, Long::parseLong);
        registerNodeRunner(EsWordArgument.class, this::processWord, this::tabWord);
        registerNodeRunner(EsEnumArgument.class, this::processEnum, this::tabEnum);
        registerNodeRunner(EsCoordinateNode.class, this::processCoordinate, this::tabCoordinate);
    }

    private <T extends EsArgumentNode, N extends Number> void registerNumberRunner(Class<T> clazz, NumberParser<N> parser) {
        registerNodeRunner(clazz,
                (context, node, args) -> processNumber(context, node, (StringBuilder) args, parser),
                (context, node, tbc) -> processNumber(context, node, ((TabCompleteContext)tbc).args, parser)
        );
    }

    @Override
    public void registerCommand(EsLiteralNode root) {
        tree.add(root);
    }

    public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        for (EsLiteralNode root : tree) {
            if (root.label.equals(label)) {
                StringBuilder buildArgs = new StringBuilder(label).append(' ').append(String.join(" ", args));
                EsCommandContext context = new EsCommandContext(BukkitHelper.fromBukkitCommandSender(sender));
                executeNode(root, context, buildArgs);
                break;
            }
        }
    }

    // true means stop execution
    private boolean executeNode(EsCommandNode node, EsCommandContext context, StringBuilder args) {
        if (!node.matchConditions(context) || !processNodeTypes(context, node, false, args)) {
            return false;
        }

        for (EsCommandNode child : node.getChildren()) {
            if (executeNode(child, context, args)) {
                return true;
            }
        }

        if (args.length() != 0) {
            EsToolsCommand.send(context.sender(), "&cUnused args? " + args);
            return true;
        }

        node.run(context);
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        List<String> complete = new ArrayList<>(0);

        for (EsLiteralNode root : tree) {
            if (root.label.equals(label)) {
                StringBuilder buildArgs = new StringBuilder(label).append(' ').append(String.join(" ", args));
                TabCompleteContext tbc = new TabCompleteContext(buildArgs);

                EsCommandContext context = new EsCommandContext(BukkitHelper.fromBukkitCommandSender(sender));
                executeTabComplete(root, context, tbc);

                complete = tbc.complete;
                break;
            }
        }

        return complete;
    }

    // true means stop execution
    private boolean executeTabComplete(EsCommandNode node, EsCommandContext context, TabCompleteContext tbc) {
        if (!node.matchConditions(context) || !processNodeTypes(context, node, true, tbc)) {
            return false;
        }

        tbc.complete.clear();

        for (EsCommandNode child : node.getChildren()) {
            if (executeTabComplete(child, context, tbc)) {
                return true;
            }
        }

        return true;
    }

    private boolean processLiteral(EsCommandContext context, EsLiteralNode node, StringBuilder args) {
        // if args has this literal, then remove it and move on
        if (args.length() >= node.label.length() + 1 && args.substring(0, node.label.length()).equals(node.label) && args.charAt(node.label.length()) == ' ') {
            args.delete(0, node.label.length()+1);
            return true;
        }

        return false;
    }

    private boolean tabLiteral(EsCommandContext context, EsLiteralNode node, TabCompleteContext tbc) {
        boolean result = processLiteral(context, node, tbc.args);
        if (result) {
            return true;
        }

        tbc.complete.add(node.label);
        return false;
    }

    private boolean processString(EsCommandContext context, EsStringNode node, StringBuilder args) {
        if (args.length() == 0) {
            return false;
        }

        int index = 0;
        if (args.charAt(0) == '"') {
            do {
                index = args.indexOf("\"", index+1);

                if (index == -1) {
                    return false;
                }
            } while (args.charAt(index-1) == '\\');

            context.addArgument(node.id(), args.substring(1, index-1));
            args.delete(0, index+1);
            return true;
        }

        index = args.indexOf(" ");

        if (index == -1) {
            index = args.length();
        }

        context.addArgument(node.id(), args.substring(0, index));
        args.delete(0, index+1);
        return true;
    }

    private boolean tabString(EsCommandContext context, EsStringNode node, TabCompleteContext tbc) {
        return processString(context, node, tbc.args);
    }

    private <T extends Number> boolean processNumber(EsCommandContext context, EsArgumentNode node, StringBuilder args, NumberParser<T> func) {
        int index = args.indexOf(" ");
        if (index == -1) {
            index = args.length();
        }

        T value;
        try {
            value = func.create(args.substring(0, index));
        } catch (NumberFormatException ignored) {
            return false;
        }

        context.addArgument(node.id(), value);
        args.delete(0, index + 1);
        return true;
    }

    private boolean processWord(EsCommandContext context, EsWordArgument node, StringBuilder args) {
        int index = args.indexOf(" ");
        if (index == -1) {
            index = args.length();
        }

        String word = args.substring(0, index);
        if (!node.isValidWord(word)) {
            return false;
        }

        context.addArgument(node.id(), word);
        args.delete(0, index + 1);
        return true;
    }

    private boolean tabWord(EsCommandContext context, EsWordArgument node, TabCompleteContext tbc) {
        boolean result = processWord(context, node, tbc.args);
        if (result) {
            return true;
        }

        tbc.complete.addAll(node.getWords());
        return false;
    }

    private boolean processEnum(EsCommandContext context, EsEnumArgument<?> node, StringBuilder args) {
        int index = args.indexOf(" ");
        if (index == -1) {
            index = args.length();
        }

        String word = args.substring(0, index);
        if (!node.isValidName(word)) {
            return false;
        }

        context.addArgument(node.id(), node.getValue(word));
        args.delete(0, index + 1);
        return true;
    }

    private boolean tabEnum(EsCommandContext context, EsEnumArgument<?> node, TabCompleteContext tbc) {
        boolean result = processEnum(context, node, tbc.args);
        if (result) {
            return true;
        }

        tbc.complete.addAll(node.getNames());
        return false;
    }

    private boolean processCoordinate(EsCommandContext context, EsCoordinateNode node, StringBuilder args) {
        EsRelativePosition pos = new BukkitRelativePosition();
        StringBuilder newArgs = new StringBuilder(args);
        boolean hasLookRelative = false;

        for (int i = 0; i < 3; i++) {
            if (newArgs.length() == 0) {
                return false;
            }

            int index = newArgs.indexOf(" ");
            if (index == -1) {
                index = newArgs.length();
            }

            // get if relative position
            if (newArgs.charAt(0) == '^') { // relative to senders position and look direction
                pos.setRelativeType(i, EsRelativePosition.RelativeType.SENDER_LOOK);
                newArgs.deleteCharAt(0);
                index--;
                hasLookRelative = true;
            } else {
                if (hasLookRelative) {
                    return false;
                }

                if (newArgs.charAt(0) == '~') { // relative to senders position
                    pos.setRelativeType(i, EsRelativePosition.RelativeType.SENDER);
                    newArgs.deleteCharAt(0);
                    index--;
                }
            }

            String valueStr = newArgs.substring(0, index);
            if (valueStr.isEmpty()) {
                pos.setPos(i, 0);
            } else {
                try {
                    pos.setPos(i, Double.parseDouble(valueStr));
                } catch (NumberFormatException ignored) {
                    return false;
                }
            }

            newArgs.delete(0, index + 1);
        }

        context.addArgument(node.id(), pos);

        args.delete(0, args.length());
        args.append(newArgs);
        return true;
    }

    private boolean tabCoordinate(EsCommandContext context, EsCoordinateNode node, TabCompleteContext tbc) {
        boolean result = processCoordinate(context, node, tbc.args);
        if (result) {
            return true;
        }

        String[] spl = tbc.args.toString().split(" ", 4);

        for (int i = 0; i < Math.min(3, spl.length); i++) {
            if (!stringOnlyContains(spl[i], "~^-.1234567890")) {
                return false;
            }
        }

        String lastArg = spl[spl.length-1];
        StringBuilder tab = new StringBuilder();
        int size = spl.length;
        if (lastArg.endsWith(" ")) {
            size--;
        }
        else if (!lastArg.isEmpty()) {
            tab.append(lastArg).append(' ');
            size++;
        }

        for (int i = 0; i < 4 - size; i++) {
            tab.append("~ ");
        }

        tab.delete(tab.length()-1, tab.length());
        tbc.complete.add(tab.toString());

        return false;
    }

    private boolean stringOnlyContains(String string, String contains) {
        for (int i = 0; i < string.length(); i++) {
            if (!contains.contains(String.valueOf(string.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    @FunctionalInterface
    private interface NumberParser<T> {
        T create(String value);
    }

    private static class TabCompleteContext {
        public StringBuilder args;
        public List<String> complete;

        public TabCompleteContext(StringBuilder args) {
            this.args = args;
            this.complete = new ArrayList<>();
        }
    }
}
