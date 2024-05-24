package net.serble.estools;

import net.serble.estools.Commands.SafeTp;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.UUID;

public class Tester {
    private final EsPlayer player;
    private int currentCommand = 0;

    public static final HashMap<UUID, Tester> runningTests = new HashMap<>();

    @SuppressWarnings("unchecked")  // It doesn't allow specifying type
    private static final Tuple<String, Double>[] routine = new Tuple[] {
            new Tuple<>("gmsp", 1.5),
            new Tuple<>("gmc", 1.5),
            new Tuple<>("gma", 1.5),
            new Tuple<>("gms", 1.5),
            new Tuple<>("i apple", 2),
            new Tuple<>("h dirt", 2),
            new Tuple<>("fly", 4),
            new Tuple<>("flyspeed 10", 2),
            new Tuple<>("flyspeed 2", 0.1),
            new Tuple<>("fly", 0.1),
            new Tuple<>("walkspeed 10", 3),
            new Tuple<>("walkspeed 2", 0.1),
            new Tuple<>("ps", 0.1),
            new Tuple<>("pp", 0.1),
            new Tuple<>("pa", 0.1),
            new Tuple<>("psh", 0.1),
            new Tuple<>("ph", 3),
            new Tuple<>("tphere {randomplayer}", 3),
            new Tuple<>("tpall", 2),
            new Tuple<>("warps add test", 2),
            new Tuple<>("safetp", 2),
            new Tuple<>("tp ~50 ~5000 ~50", 1),
            new Tuple<>("warp test", 2),
            new Tuple<>("warps remove test", 2),
            new Tuple<>("tp ~50 ~5000 ~50", 1),
            new Tuple<>("back", 1),
            new Tuple<>("cchest", -1),
            new Tuple<>("ci", 1),
            new Tuple<>("h sign", 0.1),
            new Tuple<>("msg {player} Place and look at the sign (leave it empty)", -1),
            new Tuple<>("editsign 1 Hello World!", 1),
            new Tuple<>("editsign glow", 1),
            new Tuple<>("editsign unglow", 1),
            new Tuple<>("editsign 2 Does it work?", 1),
            new Tuple<>("msg {player} Look at the back of the sign", -1),
            new Tuple<>("editsign 1 Hello World!", 1),
            new Tuple<>("editsign glow", 1),
            new Tuple<>("editsign unglow", 1),
            new Tuple<>("editsign 2 Does it work?", 1),
            new Tuple<>("eff speed 1 60 {player}", 3),
            new Tuple<>("h iron_sword", 0.1),
            new Tuple<>("ench knockback 10", 3),
            new Tuple<>("estools", 1),
            new Tuple<>("sethealth 1", 2),
            new Tuple<>("sethunger 1", 2),
            new Tuple<>("feed", 0.1),
            new Tuple<>("setsaturation 0", 0.1),
            new Tuple<>("msg {player} If you heal health and don't lose hunger, setsaturation didn't work", 6),
            new Tuple<>("heal", 1),
            new Tuple<>("msg {player} Mine a block with the sword and hold it", -1),
            new Tuple<>("fix", 1),
            new Tuple<>("getinfo {player}", 3),
            new Tuple<>("god", 0.1),
            new Tuple<>("tp ~ ~10 ~", 5),
            new Tuple<>("smite {player}", 2),
            new Tuple<>("god", 0.1),
            new Tuple<>("hideflags", 4),
            new Tuple<>("h dirt", 0.1),
            new Tuple<>("infinite", -1),
            new Tuple<>("invsee {randomplayer}", -1),
            new Tuple<>("h dirt", 0.1),
            new Tuple<>("lore add Silly &ldirt", 4),
            new Tuple<>("lore insert 1 &cNot very", 2),
            new Tuple<>("lore remove 1", 2),
            new Tuple<>("music", 3),
            new Tuple<>("night", 1),
            new Tuple<>("day", 1),
            new Tuple<>("potion speed", 2),
            new Tuple<>("rename &6This dirt is special", 4),
            new Tuple<>("setmaxhealth 30", 2),
            new Tuple<>("setstack 127", 2),
            new Tuple<>("h iron_sword", 0.1),
            new Tuple<>("setunbreakable", 4),
            new Tuple<>("heal", 0.1),
            new Tuple<>("sudo {randomplayer} say I have been sudoed", 2),
            new Tuple<>("h dirt", 0.1),
            new Tuple<>("setpersistentdata estools:hello string wassup", 2),
            new Tuple<>("getpersistentdata estools:hello string", 2),
            new Tuple<>("removepersistentdata estools:hello", 2),
            new Tuple<>("getpersistentdata estools:hello string", 2),
            new Tuple<>("suicide", 4)
    };

    public Tester(EsPlayer p) {
        player = p;
    }

    public void startTests() {
        EsToolsCommand.send(player, "&aStarting tests...");

        // Init some stuff
        SafeTp.enabled = false;

        execNextCommand();
    }

    public void continueTests() {
        execNextCommand();
    }

    private void execNextCommand() {
        if (routine.length == currentCommand) {
            EsToolsCommand.send(player, "&aTests complete!");
            runningTests.remove(player.getUniqueId());
            return;
        }

        Tuple<String, Double> cCmd = routine[currentCommand];
        currentCommand++;

        String cmd = cCmd.a();
        if (cmd.contains("{randomplayer}")) {
            // Find a random player that isn't player
            EsPlayer randomPlayer = null;
            for (EsPlayer p : Main.server.getOnlinePlayers()) {
                if (p != player) {
                    randomPlayer = p;
                    break;
                }
            }

            if (randomPlayer == null) {
                randomPlayer = player;
            }

            cmd = cmd.replace("{randomplayer}", randomPlayer.getName());
        }
        cmd = cmd.replace("{player}", player.getName());

        try {
            exec(cmd);
        } catch (Exception e) {
            EsToolsCommand.send(player, "&cFailed to execute the following command: " + cmd);

            // Print the full stacktrace not just the message
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            EsToolsCommand.send(player, "&c" + stackTrace);
            Main.logger.severe(stackTrace);
            return;
        }

        Double secVal;
        Object val = cCmd.b();
        //noinspection ConstantValue, it's not constant
        if (val instanceof Integer) {  // Stupid java being dumb
            secVal = ((Integer) val).doubleValue();
        } else {
            secVal = cCmd.b();
        }

        if (secVal < 0) {
            EsToolsCommand.send(player, "&bWaiting for &6/estools test &bto continue...");
            return;
        }

        @SuppressWarnings("WrapperTypeMayBePrimitive") Double timeInTicks = (secVal * 20.0);  // The type cannot be primitive
        Main.server.runTaskLater(this::execNextCommand, timeInTicks.longValue());
        EsToolsCommand.send(player, "&bWaiting &6" + cCmd.b() + " seconds");
    }

    private void exec(String cmd) {
        EsToolsCommand.send(player, "&aExecuting: " + cmd);
        Main.server.dispatchCommand(player, cmd);
    }
}
