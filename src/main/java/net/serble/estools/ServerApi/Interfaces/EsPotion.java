package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.EsPotionEffect;

public interface EsPotion extends EsItemStack {
    EsPotionEffect[] getEffects();
    EsPotType getPotionType();
    void addEffect(EsPotionEffect effect);
}
