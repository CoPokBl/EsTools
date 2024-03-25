package net.serble.estools.Signs;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Repair extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        if (!takeMoney(lines[1], p))
            return;

        ItemStack is = CMD.getMainHand(p);

        if (is == null)
            return;

        if (Main.version > 12) {
            ItemMeta im = is.getItemMeta();

            if (im == null) return;

            ((Damageable) im).setDamage(0);

            is.setItemMeta(im);
        } else {
            is.setDurability((short) 0);
        }
    }
}
