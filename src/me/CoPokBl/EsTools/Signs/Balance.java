package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.entity.Player;

public class Balance extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        CMD.s(p, "&aYour current balance is &6$%s", String.valueOf(Main.econ.getBalance(p)));
    }
}
