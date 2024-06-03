package net.serble.estools;

import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

import java.util.List;
import java.util.Objects;

// TODO: This is Bukkit specific (But still works on other platforms), migrate commands away from using it
// This class exists because Minecraft < 1.4 doesn't have ItemMeta. It just encapsulates all ItemMeta methods.
public class MetaHandler {

    public static EsItemStack getPotion(EsCommandSender sender, EsPotType potType, String effect, int duration, int amp, int amount) {
        EsItemStack pot = Main.server.createPotion(potType, effect, duration, amp, amount);
        if (pot == null) {
            EsToolsCommand.send(sender, "&cInvalid potion effect");
            return null;
        }

        return pot;
    }

    public static void renameItem(EsItemStack item, String name) {
        EsItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    public static List<String> getLore(EsItemStack item) {
        return item.getItemMeta().getLore();
    }

    public static void setLore(EsItemStack item, List<String> lore) {
        EsItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
