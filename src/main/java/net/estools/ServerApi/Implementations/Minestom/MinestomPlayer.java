package net.estools.ServerApi.Implementations.Minestom;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsGameMode;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsSound;
import net.estools.ServerApi.EsSoundCategory;
import net.estools.ServerApi.Interfaces.*;
import net.minestom.server.entity.Player;

public class MinestomPlayer extends MinestomLivingEntity implements EsPlayer {
    private final Player entity;

    public MinestomPlayer(Player entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public int getFoodLevel() {
        return entity.getFood();
    }

    @Override
    public void setFoodLevel(int val) {
        entity.setFood(val);
    }

    @Override
    public double getSaturation() {
        return entity.getFoodSaturation();
    }

    @Override
    public void setSaturation(double val) {
        entity.setFoodSaturation((float) val);
    }

    @Override
    public EsItemStack getMainHand() {
        throw new NotImplementedException();
    }

    @Override
    public void setMainHand(EsItemStack item) {
        throw new NotImplementedException();
    }

    @Override
    public void openInventory(EsInventory inv) {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public EsPlayerInventory getInventory() {
        return null;
    }

    @Override
    public void setFlySpeed(float val) {

    }

    @Override
    public void setWalkSpeed(float val) {

    }

    @Override
    public void setGameMode(EsGameMode mode) {

    }

    @Override
    public EsGameMode getGameMode() {
        return null;
    }

    @Override
    public EsBlock getTargetBlock() {
        return null;
    }

    @Override
    public boolean getAllowFlight() {
        return false;
    }

    @Override
    public void setAllowFlight(boolean val) {

    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public void playSound(EsSound sound, EsSoundCategory category, EsLocation loc, int volume, int pitch) {

    }

    @Override
    public void updateInventory() {

    }
}
