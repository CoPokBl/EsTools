package net.serble.estools.Signs;

import net.serble.estools.CMD;
import org.bukkit.entity.Player;

import static net.serble.estools.CMD.setHealth;

public class Heal extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        if (!takeMoney(lines[1], p))
            return;

        setHealth(p, CMD.getMaxHealth(p));
        p.setFireTicks(0);
    }
}
