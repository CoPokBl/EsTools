package net.serble.estools.Signs;

import net.serble.estools.Vault;
import org.bukkit.entity.Player;

public class Balance extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        send(p, "&aYour current balance is &6$%s", String.valueOf(Vault.economy.getBalance(p)));
    }
}
