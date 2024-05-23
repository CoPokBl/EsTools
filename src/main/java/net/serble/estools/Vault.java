package net.serble.estools;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPlayer;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
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

    public static boolean takeMoney(double price, EsPlayer p) {
        if (price <= 0) {
            return true;
        }

        if (Vault.economy == null) {
            EsToolsCommand.send(p, "&cVault is required for economy!");
            return false;
        }

        if (Vault.economy.getBalance(((BukkitPlayer) p).getBukkitPlayer()) < price) {
            EsToolsCommand.send(p, "&cYou do not have enough money to purchase this item.");
            return false;
        }

        Vault.economy.withdrawPlayer(((BukkitPlayer) p).getBukkitPlayer(), price);
        return true;
    }

    public static boolean payMoney(double price, EsPlayer p) {
        if (Vault.economy == null) {
            EsToolsCommand.send(p, "&cVault is required for economy!");
            return false;
        }

        EconomyResponse economyResponse = Vault.economy.depositPlayer(((BukkitPlayer) p).getBukkitPlayer(), price);
        return economyResponse.transactionSuccess();
    }
}
