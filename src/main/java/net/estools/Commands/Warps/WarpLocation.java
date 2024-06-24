package net.estools.Commands.Warps;

import net.estools.ServerApi.EsLocation;

public class WarpLocation {
    private EsLocation location;
    private String name;
    private boolean global;

    public String getName() {
        return name;
    }

    public EsLocation getLocation() {
        return location;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setLocation(EsLocation location) {
        this.location = location;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public void setName(String name) {
        this.name = name;
    }
}
