package net.serble.estools.Signs;

import net.serble.estools.CMD;
import org.bukkit.entity.Player;

public class Heal extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        if (!takeMoney(lines[1], p))
            return;

        p.setHealth(CMD.getMaxHealth(p));
        p.setFireTicks(0);
    }
}
