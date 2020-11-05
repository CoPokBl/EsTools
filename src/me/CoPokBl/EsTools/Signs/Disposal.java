package me.CoPokBl.EsTools.Signs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Disposal extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        p.openInventory(Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cDisposal")));
    }
}
