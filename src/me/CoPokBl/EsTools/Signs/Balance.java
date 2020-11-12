package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import me.CoPokBl.EsTools.Vault;
import org.bukkit.entity.Player;

public class Balance extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        s(p, "&aYour current balance is &6$%s", String.valueOf(Vault.econ.getBalance(p)));
    }
}
