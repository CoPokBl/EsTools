package net.estools.Config.Schemas.Give;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")  // SnakeYAML needs the setters
public class GiveConfig {
    private GiveSettings settings = new GiveSettings();
    private Map<String, String> items = new HashMap<String, String>() {{
        put("boat", "oak_boat");
        put("pearl", "ender_pearl");
        put("sign", "oak_sign");
        put("button", "stone_button");
        put("sapling", "oak_sapling");
        put("log", "oak_log");
        put("door", "oak_door");
        put("fence", "oak_fence");
        put("fence_gate", "oak_fence_gate");
        put("leaves", "oak_leaves");
        put("planks", "oak_planks");
        put("pressure_plate", "stone_pressure_plate");
        put("trapdoor", "oak_trapdoor");
        put("steak", "cooked_beef");
        put("water", "water_bucket");
        put("lava", "lava_bucket");
    }};

    public GiveSettings getSettings() {
        return settings;
    }

    public void setSettings(GiveSettings settings) {
        this.settings = settings;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }
}
