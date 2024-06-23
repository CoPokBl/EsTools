package net.serble.estools.Implementation;

import net.serble.estools.ServerApi.*;
import net.serble.estools.ServerApi.Interfaces.*;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")  // For when tests are added
public class TestPlayer extends TestLivingEntity implements EsPlayer {
    private final String name;
    private int foodLevel = 20;
    private double saturation = 20;
    private float flySpeed = 2;
    private float walkSpeed = 2;
    private EsGameMode gameMode = EsGameMode.Survival;
    private boolean allowFlight = false;
    private static final boolean flying = false;
    private EsSound playingSound = null;

    public TestPlayer(EsWorld world, String name) {
        super(world);
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    // TESTING METHODS

    public @Nullable EsSound getPlayingSound() {
        return playingSound;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    // IMPLEMENTATION METHODS

    @Override
    public int getFoodLevel() {
        return foodLevel;
    }

    @Override
    public void setFoodLevel(int val) {
        foodLevel = val;
    }

    @Override
    public double getSaturation() {
        return saturation;
    }

    @Override
    public void setSaturation(double val) {
        saturation = val;
    }

    @Override
    public EsItemStack getMainHand() {
        return null;
    }

    @Override
    public void setMainHand(EsItemStack item) {

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
        flySpeed = val;
    }

    @Override
    public void setWalkSpeed(float val) {
        walkSpeed = val;
    }

    @Override
    public void setGameMode(EsGameMode mode) {
        gameMode = mode;
    }

    @Override
    public EsGameMode getGameMode() {
        return gameMode;
    }

    @Override
    public EsBlock getTargetBlock() {
        return null;
    }

    @Override
    public boolean getAllowFlight() {
        return allowFlight;
    }

    @Override
    public void setAllowFlight(boolean val) {
        allowFlight = val;
    }

    @Override
    public boolean isFlying() {
        return flying;
    }

    @Override
    public void playSound(EsSound sound, EsSoundCategory category, EsLocation loc, int volume, int pitch) {
        playingSound = sound;
    }

    @Override
    public void updateInventory() {

    }

    @Override
    public EsWorld getWorld() {
        return getLocation().getWorld();
    }

    @Override
    public String getType() {
        return "PLAYER";
    }

    @Override
    public String getName() {
        return name;
    }
}
