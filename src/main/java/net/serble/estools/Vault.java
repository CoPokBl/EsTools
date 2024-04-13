package net.serble.estools;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
    public static Economy economy = null;

    public static void setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getLogger().warning("No Vault plugin found, please install vault for economy functionality");
            return;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getLogger().warning("No vault service found, make sure vault and a economy plugin is installed!");
            return;
        }

        economy = rsp.getProvider();

        //noinspection ConstantValue
        if (economy == null) {
            Bukkit.getLogger().warning("Vault functionality failed to initialise");
        }

    }

    public static boolean takeMoney(double price, Player p) {
        if (price <= 0) {
            return true;
        }

        if (Vault.economy == null) {
            EsToolsCommand.send(p, "&cVault is required for economy!");
            return false;
        }

        if (Vault.economy.getBalance(p) < price) {
            EsToolsCommand.send(p, "&cYou do not have enough money to purchase this item.");
            return false;
        }

        Vault.economy.withdrawPlayer(p, price);
        return true;
    }

    public static boolean payMoney(double price, Player p) {
        if (Vault.economy == null) {
            EsToolsCommand.send(p, "&cVault is required for economy!");
            return false;
        }

        EconomyResponse economyResponse = Vault.economy.depositPlayer(p, price);
        return economyResponse.transactionSuccess();
    }
}
