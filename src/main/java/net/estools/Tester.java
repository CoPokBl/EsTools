package net.estools;

import net.estools.Commands.SafeTp;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.HashMap;
import java.util.UUID;

public class Tester {
    private final EsPlayer player;
    private int currentCommand = 0;

    public static final HashMap<UUID, Tester> runningTests = new HashMap<>();

    private static final TestCommand[] routine = new TestCommand[] {
            new TestCommand("music", 1, new SemanticVersion(1, 9, 0)),
            new TestCommand("gmsp", 1.5, new SemanticVersion(1, 8, 0)),
            new TestCommand("gmc", 1.5),
            new TestCommand("gma", 1.5, new SemanticVersion(1, 3, 0)),
            new TestCommand("gms", 1.5),
            new TestCommand("i apple", 2),
            new TestCommand("h dirt", 2),
            new TestCommand("fly", 4, new SemanticVersion(1, 2, 0)),
            new TestCommand("flyspeed 10", 2, new SemanticVersion(1, 3, 0)),
            new TestCommand("flyspeed 2", 0.1, new SemanticVersion(1, 3, 0)),
            new TestCommand("fly", 0.1, new SemanticVersion(1, 2, 0)),
            new TestCommand("walkspeed 10", 3, new SemanticVersion(1, 4, 0)),
            new TestCommand("walkspeed 2", 0.1, new SemanticVersion(1, 4, 0)),
            new TestCommand("ps", 0.1, new SemanticVersion(1, 1, 0)),
            new TestCommand("pp", 0.1, new SemanticVersion(1, 1, 0)),
            new TestCommand("pa", 0.1, new SemanticVersion(1, 1, 0)),
            new TestCommand("psh", 0.1, new SemanticVersion(1, 1, 0)),
            new TestCommand("ph", 3, new SemanticVersion(1, 1, 0)),
            new TestCommand("tphere {randomplayer}", 3),
            new TestCommand("tpall", 2),
            new TestCommand("warps add test", 2),
            new TestCommand("safetp", 2, new SemanticVersion(1, 1, 0)),
            new TestCommand("tp {player} 0 512 0", 1),
            new TestCommand("warp test", 2),
            new TestCommand("warps remove test", 2),
            new TestCommand("tp {player} 0 512 0", 1, new SemanticVersion(1, 1, 0)),
            new TestCommand("back", 1, new SemanticVersion(1, 1, 0)),
            new TestCommand("cchest", -1, new SemanticVersion(1, 7, 0)),
            new TestCommand("i apple 64", 0.1, new SemanticVersion(1, 1, 2)),
            new TestCommand("disposal", -1, new SemanticVersion(1, 1, 2)),
            new TestCommand("ci", 1),
            new TestCommand("h sign", 0.1),
            new TestCommand("msg Place and look at the sign (leave it empty)", -1),
            new TestCommand("editsign 1 Hello World!", 1),
            new TestCommand("editsign glow", 1, new SemanticVersion(1, 17,0)),
            new TestCommand("editsign unglow", 1, new SemanticVersion(1, 17, 0)),
            new TestCommand("editsign 2 Does it work?", 1),
            new TestCommand("msg {player} Look at the back of the sign", -1, new SemanticVersion(1, 20, 0)),
            new TestCommand("editsign 1 Hello World!", 1, new SemanticVersion(1, 20, 0)),
            new TestCommand("editsign glow", 1, new SemanticVersion(1, 20, 0)),
            new TestCommand("editsign unglow", 1, new SemanticVersion(1, 20, 0)),
            new TestCommand("editsign 2 Does it work?", 1, new SemanticVersion(1, 20 ,0)),
            new TestCommand("eff speed 1 60 {player}", 3, new SemanticVersion(1, 1, 0)),
            new TestCommand("h iron_sword", 0.1),
            new TestCommand("ench knockback 10", 3, new SemanticVersion(1, 1, 0)),
            new TestCommand("estools", 0.1),
            new TestCommand("sethealth 1", 2),
            new TestCommand("sethunger 1", 2),
            new TestCommand("feed", 0.1),
            new TestCommand("setsaturation 0", 0.1),
            new TestCommand("msg If you heal health and don't lose hunger, setsaturation didn't work", 6),
            new TestCommand("heal", 1),
            new TestCommand("msg Mine a block with the sword and hold it", -1),
            new TestCommand("fix", 1),
            new TestCommand("getinfo {player}", 3),
            new TestCommand("god", 0.1, new SemanticVersion(1, 1, 0)),
            new TestCommand("smite {player}", 2),
            new TestCommand("god", 0.1, new SemanticVersion(1, 1, 0)),
            new TestCommand("hideflags", 4, new SemanticVersion(1, 8, 0)),
            new TestCommand("h dirt", 0.1),
            new TestCommand("infinite", -1, new SemanticVersion(1, 1, 0)),
            new TestCommand("invsee {randomplayer}", -1, new SemanticVersion(1, 2, 0)),
            new TestCommand("h dirt", 0.1),
            new TestCommand("lore add Silly &ldirt", 4, new SemanticVersion(1, 4, 6)),
            new TestCommand("lore insert 1 &cNot very", 2, new SemanticVersion(1, 4, 6)),
            new TestCommand("lore remove 1", 2, new SemanticVersion(1, 4, 6)),
            new TestCommand("rain", 3),
            new TestCommand("sun", 3),
            new TestCommand("thunder", 3),
            new TestCommand("night", 1),
            new TestCommand("noon", 1),
            new TestCommand("midnight", 1),
            new TestCommand("day", 1),
            new TestCommand("potion speed", 2),
            new TestCommand("rename &6This dirt is special", 4, new SemanticVersion(1, 4, 6)),
            new TestCommand("setmaxhealth 30", 2, new SemanticVersion(1, 4, 0)),
            new TestCommand("setstack 127", 2),
            new TestCommand("h iron_sword", 0.1),
            new TestCommand("setunbreakable", 4, new SemanticVersion(1, 1, 0)),
            new TestCommand("heal", 0.1),
            new TestCommand("sudo {randomplayer} say I have been sudoed", 2),
            new TestCommand("h dirt", 0.1),
            new TestCommand("setpersistentdata estools:hello string wassup", 2, new SemanticVersion(1, 14, 0)),
            new TestCommand("getpersistentdata estools:hello string", 2, new SemanticVersion(1, 14, 0)),
            new TestCommand("removepersistentdata estools:hello", 2, new SemanticVersion(1, 14, 0)),
            new TestCommand("getpersistentdata estools:hello string", 2, new SemanticVersion(1, 14, 0)),
            new TestCommand("mount {entity}", 2),
            new TestCommand("dismount", 1),
            new TestCommand("dismount", 1),
            new TestCommand("mount {player}", 1),
            new TestCommand("mount {player} {entity}", 2),
            new TestCommand("dismount {entity}", 1),
            new TestCommand("sethealth 1", 0.1),
            new TestCommand("buddha", -1, new SemanticVersion(1, 1, 0)),
            new TestCommand("suicide", 1),
    };

    public Tester(EsPlayer p) {
        player = p;
    }

    public void startTests() {
        EsToolsCommand.send(player, "&aStarting tests...");

        // Init some stuff
        SafeTp.enabled = false;

        // Find starting command (Where the version is valid)
        while (routine[currentCommand].getMinVersion() != null && !Main.minecraftVersion.isAtLeast(routine[currentCommand].getMinVersion())) {
            currentCommand++;
        }

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

        TestCommand cCmd = routine[currentCommand];
        do {
            currentCommand++;
        } while (routine.length != currentCommand && routine[currentCommand].getMinVersion() != null && !Main.minecraftVersion.isAtLeast(routine[currentCommand].getMinVersion()));

        String cmd = cCmd.getCmd();
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

        if (cmd.contains("{entity}")) {
            // Find the nearest entity
            EsEntity nearestEntity = null;
            double nearestDistance = 60000000;

            for (EsEntity en : player.getWorld().getEntities()) {
                if (en instanceof EsPlayer) {
                    continue;
                }

                double distance = player.getLocation().distanceTo(en.getLocation());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestEntity = en;
                }
            }

            if (nearestEntity == null) {
                nearestEntity = player;
            }

            cmd = cmd.replace("{entity}", nearestEntity.getUniqueId().toString());
        }

        cmd = cmd.replace("{player}", player.getName());

        try {
            exec(cmd);
        } catch (Exception e) {
            EsToolsCommand.send(player, "&cFailed to execute the following command: " + cmd);

            // Print the full stacktrace not just the message
            String stackTrace = Utils.getStacktrace(e);

            EsToolsCommand.send(player, "&c" + stackTrace);
            Main.logger.severe(stackTrace);
            return;
        }

        double secVal;
        Object val = cCmd.getWaitAfter();
        //noinspection ConstantValue, it's not constant
        if (val instanceof Integer) {  // Stupid java being dumb
            secVal = ((Integer) val).doubleValue();
        } else {
            secVal = cCmd.getWaitAfter();
        }

        if (secVal < 0) {
            EsToolsCommand.send(player, "&bWaiting for &6/estools test &bto continue...");
            return;
        }

        @SuppressWarnings("WrapperTypeMayBePrimitive") Double timeInTicks = (secVal * 20.0);  // The type cannot be primitive
        Main.server.runTaskLater(this::execNextCommand, timeInTicks.longValue());
        EsToolsCommand.send(player, "&bWaiting &6" + cCmd.getWaitAfter() + " seconds");
    }

    private void exec(String cmd) {
        if (cmd.startsWith("msg ")) {  // Overwrite msg to make a fancier way to talking to the player
            EsToolsCommand.send(player, "&b[Tester] " + cmd.replace("msg ", ""));
            return;
        }
        EsToolsCommand.send(player, "&aExecuting: " + cmd);
        Main.server.dispatchCommand(player, cmd);
    }
}
