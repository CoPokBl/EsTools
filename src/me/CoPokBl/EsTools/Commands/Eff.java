package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.MultiPlayerCommand;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class Eff extends MultiPlayerCommand {

    public static final String usage = genUsage("/eff <effect> <amplifier> <duration> <players>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            s(sender, usage);
            return true;
        }

        PotionEffectType effect = PotionEffectType.getByName(args[0]);

        if (effect == null) {
            s(sender, "&cEffect not found!");
            return true;
        }

        int amplifier = 0;

        if (args.length > 1) {
            try {
                amplifier = Integer.parseInt(args[1]) - 1;
            } catch (Exception x) {
                s(sender, usage);
                return true;
            }
        }

        s(sender, "A: %d", amplifier);

        int duration = 600;

        if (args.length > 2) {
            try {
                duration = Integer.parseInt(args[2]) * 20;
            } catch (Exception x) {
                s(sender, usage);
                return true;
            }
        }

        s(sender, "D: %d", duration);

        ArrayList<Player> ps = new ArrayList<>();

        if (args.length < 4) {
            if (isNotPlayer(sender, usage))
                return true;

            ps.add((Player)sender);
        } else {
            ps = getPlayers(sender, removeArgs(args, 3));

            if (ps.isEmpty())
                return true;
        }

        for (Player p : ps) {
            p.addPotionEffect(new PotionEffect(effect, duration, amplifier));
        }

        s(sender, "&aAdded effect &6%s&a at level &6%s&a for &6%s Seconds", effect.getName(), amplifier + 1, duration / 20);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        switch (args.length) {
            case 1:
                for (PotionEffectType e : PotionEffectType.values()) {
                    tab.add(e.getName().toLowerCase());
                }
                break;

            case 2:
                tab.add("1");
                break;

            case 3:
                tab.add("60");
                break;

            case 4:
                return super.tabComplete(sender, args, lArg);
        }

        return tab;
    }
}
