package net.serble.estools;

import net.serble.estools.Commands.SafeTp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Tester {
    private final Player player;
    private int currentCommand = 0;

    @SuppressWarnings("unchecked")  // It doesn't allow specifying type
    private static final Tuple<String, Double>[] routine = new Tuple[] {
            new Tuple<>("gmsp", 1),
            new Tuple<>("gmc", 1),
            new Tuple<>("gma", 1),
            new Tuple<>("gms", 1),
            new Tuple<>("i apple", 1),
            new Tuple<>("h dirt", 1),
            new Tuple<>("fly", 3),
            new Tuple<>("flyspeed 10", 2),
            new Tuple<>("flyspeed 2", 0.1),
            new Tuple<>("fly", 0.1),
            new Tuple<>("walkspeed 10", 2),
            new Tuple<>("walkspeed 2", 0.1),
            new Tuple<>("pa", 0.1),
            new Tuple<>("ph", 0.1),
            new Tuple<>("pp", 0.1),
            new Tuple<>("psh", 0.1),
            new Tuple<>("ps", 3),
            new Tuple<>("tphere {randomplayer}", 1),
            new Tuple<>("tpall", 1),
            new Tuple<>("warps add test", 0.5),
            new Tuple<>("safetp", 1),
            new Tuple<>("tp ~50 ~5000 ~50", 2),
            new Tuple<>("warp test", 2),
            new Tuple<>("warps remove test", 0.1),
            new Tuple<>("tp ~50 ~5000 ~50", 1),
            new Tuple<>("back", 1),
            new Tuple<>("cchest", 5),
            new Tuple<>("ci", 1),
            new Tuple<>("h sign", 0.1),
            new Tuple<>("msg {player} Place and look at the sign (leave it empty)", 3),
            new Tuple<>("editsign 1 Hello World!", 1),
            new Tuple<>("editsign glow", 1),
            new Tuple<>("editsign unglow", 1),
            new Tuple<>("editsign 2 Does it work?", 1),
            new Tuple<>("eff speed 1 60 {player}", 2),
            new Tuple<>("h iron_sword", 0.1),
            new Tuple<>("ench knockback 10", 2),
            new Tuple<>("estools", 1),
            new Tuple<>("sethunger 1", 1),
            new Tuple<>("feed", 1),
            new Tuple<>("setsaturation 20", 1),
            new Tuple<>("msg {player} Mine a block with the sword and hold it", 3),
            new Tuple<>("fix", 1),
            new Tuple<>("getinfo {player}", 3),
            new Tuple<>("god", 0.1),
            new Tuple<>("tp ~ ~10 ~", 4),
            new Tuple<>("god", 0.1),
            new Tuple<>("sethealth 1", 1),
            new Tuple<>("heal", 1),
            new Tuple<>("hideflags", 2),
            new Tuple<>("h dirt", 0.1),
            new Tuple<>("infinite", 5),
            new Tuple<>("invsee {randomplayer}", 3),
            new Tuple<>("h dirt", 0.1),
            new Tuple<>("lore add Silly dirt", 2),
            new Tuple<>("lore remove 1", 2),
            new Tuple<>("music", 3),
            new Tuple<>("night", 1),
            new Tuple<>("day", 1),
            new Tuple<>("potion speed", 2),
            new Tuple<>("rename &6This dirt is special", 2),
            new Tuple<>("setmaxhealth 10", 1),
            new Tuple<>("setstack 127", 1),
            new Tuple<>("h iron_sword", 0.1),
            new Tuple<>("setunbreakable", 3),
            new Tuple<>("smite {player}", 1),
            new Tuple<>("heal", 0.1),
            new Tuple<>("sudo {randomplayer} say I have been sudoed", 1),
            new Tuple<>("h dirt", 0.1),
            new Tuple<>("setpersistentdata estools:hello string wassup", 1),
            new Tuple<>("getpersistentdata estools:hello string", 1),
            new Tuple<>("removepersistentdata estools:hello", 1),
            new Tuple<>("suicide", 0.1)
    };

    public Tester(Player p) {
        player = p;
    }

    public void startTests() {
        EsToolsCommand.send(player, "&aStarting tests...");

        // Init some stuff
        SafeTp.enabled = false;

        execNextCommand();
    }

    private void execNextCommand() {
        if (routine.length == currentCommand) {
            EsToolsCommand.send(player, "&aTests complete!");
            return;
        }

        Tuple<String, Double> cCmd = routine[currentCommand];
        currentCommand++;

        String cmd = cCmd.a();
        if (cmd.contains("{randomplayer}")) {
            // Find a random player that isn't player
            Player randomPlayer = null;
            for (Player p : Bukkit.getOnlinePlayers()) {
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
            Bukkit.getLogger().severe(stackTrace);
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
        @SuppressWarnings("WrapperTypeMayBePrimitive") Double timeInTicks = (secVal * 20.0);  // The type cannot be primitive
        Bukkit.getScheduler().runTaskLater(Main.plugin, this::execNextCommand, timeInTicks.longValue());
        EsToolsCommand.send(player, "&bWaiting " + cCmd.b() + " seconds");
    }

    private void exec(String cmd) {
        EsToolsCommand.send(player, "&aExecuting: " + cmd);
        Bukkit.dispatchCommand(player, cmd);
    }

}
