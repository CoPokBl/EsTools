package net.serble.estools;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Logger;

public class Vault {
    public static Economy econ = null;

    public static boolean setupEconomy() {
        Logger l = Bukkit.getLogger();

        if (Main.current.getServer().getPluginManager().getPlugin("Vault") == null) {
            l.warning("No Vault plugin found, please install vault for economy functionality");
            return false;
        }



        RegisteredServiceProvider<Economy> rsp = Main.current.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            l.warning("No vault service found, make sure vault and a economy plugin is installed!");
            return false;
        }

        econ = rsp.getProvider();

        if (econ == null) {
            l.warning("Vault functionality failed to initialise");
            return false;
        }

        return true;
    }



    public static boolean hasMoney(double price, Player p) {
        if (price > 0) {
            if (Vault.econ == null) {
                CMD.s(p, "&cVault is required for economy!");
                return false;
            }

            return Vault.econ.getBalance(p) >= price;
        }

        return true;
    }

    public static boolean takeMoney(double price, Player p) {
        if (price > 0) {
            if (Vault.econ == null) {
                CMD.s(p, "&cVault is required for economy!");
                return false;
            }

            if (Vault.econ.getBalance(p) >= price) {
                Vault.econ.withdrawPlayer(p, price);
            } else {
                CMD.s(p, "&cYou do not have enough money to purchase this item.");
                return false;
            }
        }

        return true;
    }

    public static boolean payMoney(double price, Player p) {
        if (Vault.econ == null) {
            CMD.s(p, "&cVault is required for economy!");
            return false;
        }

        EconomyResponse e = Vault.econ.depositPlayer(p, price);
        return e.transactionSuccess();
    }
}
