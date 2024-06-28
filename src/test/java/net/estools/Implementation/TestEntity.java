package net.estools.Implementation;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.estools.ServerApi.Interfaces.EsWorld;

import java.util.*;

@SuppressWarnings("unused")  // For when tests are added
public class TestEntity implements EsEntity {
    private String type = "GENERIC_ENTITY";
    private final UUID uuid;
    private boolean isOp = true;
    private final List<String> chatMessages = new ArrayList<>();
    private final Set<String> scoreboardTags = new HashSet<>();
    private EsLocation location;
    private int onFireTicks = 0;
    private float fallDistance = 0;
    private final List<EsEntity> passengers = new ArrayList<>();
    private EsEntity vehicle = null;

    // TESTING METHODS

    public TestEntity(EsWorld world) {
        uuid = UUID.randomUUID();
        location = new EsLocation(world, 0, 60, 0);
    }

    public TestEntity(EsWorld world, String type) {
        uuid = UUID.randomUUID();
        this.type = type;
        location = new EsLocation(world, 0, 60, 0);
    }

    public boolean isOp() {
        return isOp;
    }

    public void setOp(boolean op) {
        isOp = op;
    }

    public List<String> getChatMessages() {
        return chatMessages;
    }

    public void clearChatMessages() {
        chatMessages.clear();
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOnFireTicks() {
        return onFireTicks;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public void teleportRandom() {
        location = new EsLocation(location.getWorld(), Math.random() * 100, Math.random() * 100, Math.random() * 100);
    }

    // IMPLEMENTATION METHODS

    @Override
    public void sendMessage(String... args) {
        // combine message
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
        }
        chatMessages.add(sb.toString());
    }

    @Override
    public boolean hasPermission(String node) {
        return isOp;
    }

    @Override
    public boolean isPermissionSet(String node) {
        return true;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void teleport(EsLocation loc) {
        location = loc;
    }

    @Override
    public EsLocation getLocation() {
        return location;
    }

    @Override
    public boolean leaveVehicle() {
        if (vehicle == null) {
            return false;
        }
        vehicle.getPassengers().remove(this);
        vehicle = null;
        return true;
    }

    @Override
    public List<EsEntity> getPassengers() {
        return passengers;
    }

    @Override
    public Set<String> getScoreboardTags() {
        return scoreboardTags;
    }

    @Override
    public void addScoreboardTag(String tag) {
        scoreboardTags.add(tag);
    }

    @Override
    public boolean hasScoreboardTag(String tag) {
        return scoreboardTags.contains(tag);
    }

    @Override
    public void setOnFireTicks(int ticks) {
        onFireTicks = ticks;
    }

    @Override
    public void addPassenger(EsEntity entity) {
        passengers.add(entity);
        ((TestEntity) entity).vehicle = this;
    }

    @Override
    public void setFallDistance(float dis) {
        fallDistance = dis;
    }
}
