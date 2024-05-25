package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.serble.estools.ServerApi.Interfaces.EsEntity;

public class EsEntityDamageEvent extends EsCancellableEvent {
    private final EsEntity entity;
    private double damage;

    public EsEntityDamageEvent(EsEntity entity, double dmg) {
        this.entity = entity;
        damage = dmg;
    }

    public EsEntity getEntity() {
        return entity;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
