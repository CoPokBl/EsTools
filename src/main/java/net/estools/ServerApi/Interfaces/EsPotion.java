package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsPotType;
import net.estools.ServerApi.EsPotionEffect;

@SuppressWarnings("unused")
public interface EsPotion extends EsItemStack {
    EsPotionEffect[] getEffects();
    EsPotType getPotionType();
    void addEffect(EsPotionEffect effect);
}
