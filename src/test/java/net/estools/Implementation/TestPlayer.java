package net.estools.Implementation;

import net.estools.ServerApi.*;
import net.estools.ServerApi.Interfaces.*;
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
    private final TestPlayerInventory inventory;
    private EsInventory openInventory;

    public TestPlayer(EsWorld world, String name) {
        super(world);
        this.name = name;
        inventory = new TestPlayerInventory(this);
    }

    @Override
    public String toString() {
        return getName();
    }

    // TESTING METHODS

    public @Nullable EsSound getPlayingSound() {
        return playingSound;
    }

    public void resetPlayingSound() {
        playingSound = null;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public EsInventory getOpenInventory() {
        return openInventory;
    }

    public void setSelectedSlot(int s) {
        inventory.setSelectedSlot(s);
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
        return inventory.getMainHand();
    }

    @Override
    public void setMainHand(EsItemStack item) {
        inventory.setItem(EsEquipmentSlot.Hand, item);
    }

    @Override
    public void openInventory(EsInventory inv) {
        openInventory = inv;
    }

    @Override
    public void closeInventory() {
        openInventory = null;
    }

    @Override
    public EsPlayerInventory getInventory() {
        return inventory;
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
        // We don't have bugs like Bukkit does
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
