package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsItemFlag;

import java.util.List;
import java.util.Set;

public interface EsItemMeta {
    void setUnbreakable(boolean val);
    boolean isUnbreakable();
    Set<EsItemFlag> getItemFlags();
    void addItemFlags(EsItemFlag... flags);
    void removeItemFlags(EsItemFlag... flags);
    void setDisplayName(String val);
    String getDisplayName();
    List<String> getLore();
    void setLore(List<String> val);
}
