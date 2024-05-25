package net.serble.estools.Signs;

import net.serble.estools.ServerApi.Implementations.Folia.FoliaPlayer;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.Vault;

public class Balance extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        send(p, "&aYour current balance is &6$%s", String.valueOf(Vault.economy.getBalance(((FoliaPlayer)p).getBukkitPlayer())));
    }
}
