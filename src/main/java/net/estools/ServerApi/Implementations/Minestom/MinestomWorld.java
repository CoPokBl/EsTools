package net.estools.ServerApi.Implementations.Minestom;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.estools.ServerApi.Interfaces.EsWorld;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.Weather;

import java.util.ArrayList;
import java.util.List;

public class MinestomWorld implements EsWorld {
    private final Instance instance;

    public MinestomWorld(Instance instance) {
        this.instance = instance;
    }

    @Override
    public String getName() {
        return instance.getDimensionName();
    }

    @Override
    public List<EsEntity> getEntities() {
        return new ArrayList<>();
    }

    @Override
    public List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff) {
        return new ArrayList<>();
    }

    @Override
    public void setTime(long time) {
        instance.setTime(time);
    }

    @Override
    public void setStorming(boolean val) {
        instance.setWeather(val ? Weather.RAIN : Weather.CLEAR);
    }

    @Override
    public void setThundering(boolean val) {
        instance.setWeather(val ? Weather.THUNDER : Weather.CLEAR);
    }

    @Override
    public void strikeLightning(EsLocation loc) {
        throw new NotImplementedException();
    }
}
