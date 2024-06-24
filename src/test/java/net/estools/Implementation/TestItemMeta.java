package net.estools.Implementation;

import net.estools.ServerApi.EsItemFlag;
import net.estools.ServerApi.Interfaces.EsItemMeta;
import net.estools.ServerApi.Interfaces.EsPersistentDataContainer;

import java.util.*;

public class TestItemMeta implements EsItemMeta {
    private boolean unbreakable = false;
    private final Set<EsItemFlag> flags = new HashSet<>();
    private String displayName = "";
    private List<String> lore = new ArrayList<>();

    @Override
    public void setUnbreakable(boolean val) {
        unbreakable = val;
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable;
    }

    @Override
    public Set<EsItemFlag> getItemFlags() {
        return flags;
    }

    @Override
    public void addItemFlags(EsItemFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
    }

    @Override
    public void removeItemFlags(EsItemFlag... flags) {
        Arrays.asList(flags).forEach(this.flags::remove);
    }

    @Override
    public void setDisplayName(String val) {
        displayName = val;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public void setLore(List<String> val) {
        lore = val;
    }

    @Override
    public EsPersistentDataContainer getPersistentDataContainer() {
        return null;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")  // Why would it?
    public TestItemMeta clone() {
        TestItemMeta clone = new TestItemMeta();
        clone.unbreakable = unbreakable;
        clone.flags.addAll(flags);
        clone.displayName = displayName;
        clone.lore.addAll(lore);
        return clone;
    }
}
