package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.HashMap;

// Kept to allow for modification in the future
public class EventsHelper {
    private static final HashMap<EventType, String> oldEvents = new HashMap<EventType, String>() {{
        put(EventType.PlayerInteract, "PLAYER_INTERACT");
        put(EventType.SignChange, "SIGN_CHANGE");
        put(EventType.EntityDamage, "ENTITY_DAMAGE");
        put(EventType.PlayerDeath, "PLAYER_DEATH");
        put(EventType.PlayerTeleport, "PLAYER_TELEPORT");
        put(EventType.InventoryClick, "INVENTORY_CLICK");
        put(EventType.InventoryDrag, "INVENTORY_DRAG");  // WILL ERROR IN 1.0.0
        put(EventType.PlayerQuit, "PLAYER_QUIT");
        put(EventType.PlayerJoin, "PLAYER_JOIN");
        put(EventType.PlayerKick, "PLAYER_KICK");
        put(EventType.BlockPlace, "BLOCK_PLACE");
    }};


//    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")  // Not used but kept just in case and for reference
//    private static final HashMap<EventType, Class<? extends Event>> newEvents = new HashMap<EventType, Class<? extends Event>>() {{
//        put(EventType.PlayerInteract, PlayerInteractEvent.class);
//        put(EventType.SignChange, SignChangeEvent.class);
//        put(EventType.EntityDamage, EntityDamageEvent.class);
//        put(EventType.PlayerDeath, PlayerDeathEvent.class);
//        put(EventType.PlayerTeleport, PlayerTeleportEvent.class);
//        put(EventType.InventoryClick, InventoryClickEvent.class);
//        put(EventType.InventoryDrag, InventoryDragEvent.class);
//        put(EventType.PlayerQuit, PlayerQuitEvent.class);
//        put(EventType.PlayerJoin, PlayerJoinEvent.class);
//        put(EventType.PlayerKick, PlayerKickEvent.class);
//        put(EventType.BlockPlace, BlockPlaceEvent.class);
//    }};

    public static void registerEvents(Listener listener, EventType... eventTypes) {
        if (Main.version > 0) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, Main.current);
        } else {
            // Non functional in 1.0.0, kept for reference
//            for (EventType eventType : eventTypes) {
//                try {
//                    // Event.Type
//                    Class<?> eventTypeClass = Class.forName("org.bukkit.event.Event$Type");
//                    Field customEventField = eventTypeClass.getField(oldEvents.get(eventType));
//                    Object customEventObj = customEventField.get(null);
//
//                    // Event.Priority
//                    Class<?> priorityClass = Class.forName("org.bukkit.event.Event$Priority");
//                    Field normalPriorityField = priorityClass.getField("Normal");
//                    Object normalPriorityObj = normalPriorityField.get(null);
//
//                    PluginManager pm = Bukkit.getServer().getPluginManager();
//
//                    Method method = pm.getClass().getMethod("registerEvent", eventTypeClass, Listener.class, priorityClass, Plugin.class);
//                    method.invoke(pm, customEventObj, listener, normalPriorityObj, Main.current);
//                } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
//                    Bukkit.getLogger().severe(e.toString());
//                } catch (NoSuchMethodException | InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }
    }

    public enum EventType {
        PlayerInteract,
        SignChange,
        EntityDamage,
        PlayerDeath,
        PlayerTeleport,
        InventoryClick,
        InventoryDrag,
        PlayerQuit,
        PlayerJoin,
        PlayerKick,
        BlockPlace
    }

}
