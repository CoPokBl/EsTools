package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.entity.Player;

public class Heal extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        if (!payMoney(lines[1], p))
            return;

        p.setHealth(CMD.getMaxHealth(p));
        p.setFireTicks(0);
    }
}
