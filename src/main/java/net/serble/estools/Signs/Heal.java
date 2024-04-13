package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import org.bukkit.entity.Player;

import static net.serble.estools.EsToolsCommand.setHealth;

public class Heal extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        if (!takeMoney(lines[1], p)) {
            return;
        }

        setHealth(p, EsToolsCommand.getMaxHealth(p));
        p.setFireTicks(0);
    }
}
