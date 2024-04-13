package net.serble.estools.Signs;

import org.bukkit.entity.Player;

public class Feed extends SignType {

    @Override
    public void run(Player p, String[] lines) {
        if (!takeMoney(lines[1], p)) {
            return;
        }

        p.setFoodLevel(20);
        p.setSaturation(6);
    }
}
