package me.CoPokBl.EsTools.Signs;

import org.bukkit.entity.Player;

public class Feed extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        p.setFoodLevel(20);
        p.setSaturation(6);
    }
}
