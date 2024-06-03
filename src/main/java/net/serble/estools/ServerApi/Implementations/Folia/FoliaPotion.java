package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.EsPotionEffect;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

@SuppressWarnings("deprecation")  // This whole class depends on the deprecated Potion class
public class FoliaPotion extends FoliaItemStack implements EsPotion {
    private Potion bukkitPotion;
    private PotionMeta bukkitMeta;
    private boolean old;

    public FoliaPotion(ItemStack is) {
        super(is);
        calcOld();

        if (old) {
            bukkitMeta = (PotionMeta) is.getItemMeta();
        } else {
            bukkitPotion = Potion.fromItemStack(is);
        }
    }

    private void calcOld() {
        if (Main.minecraftVersion.getMinor() >= 9) {
            old = false;
        } else if (Main.minecraftVersion.getMinor() >= 4) {
            old = true;
        } else {
            throw new RuntimeException("Potion are not supported in this Minecraft version");
        }
    }

    @Override
    public EsPotionEffect[] getEffects() {
        PotionEffect[] in;
        if (old) {
            in = bukkitPotion.getEffects().toArray(new PotionEffect[0]);
        } else {
            in = bukkitMeta.getCustomEffects().toArray(new PotionEffect[0]);
        }

        EsPotionEffect[] out = new EsPotionEffect[in.length];
        for (int i = 0; i < out.length; i++) {
            PotionEffect effect = in[i];
            out[i] = new EsPotionEffect(effect.getType().getName(), effect.getAmplifier(), effect.getDuration());
        }
        return out;
    }

    @Override
    public EsPotType getPotionType() {
        if (old) {
            return FoliaHelper.fromBukkitPotType(bukkitPotion.toItemStack(0).getType());
        } else {
            return FoliaHelper.fromBukkitPotType(getBukkitItem().getType());
        }
    }

    @Override
    public void addEffect(EsPotionEffect effect) {
        if (old) {
            bukkitPotion.getEffects().add(BukkitHelper.toBukkitPotionEffect(effect));
        } else {
            bukkitMeta.addCustomEffect(BukkitHelper.toBukkitPotionEffect(effect), true);
        }
    }
}
