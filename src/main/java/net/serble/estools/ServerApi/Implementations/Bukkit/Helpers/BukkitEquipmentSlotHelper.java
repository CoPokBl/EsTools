package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.ServerApi.EsEquipmentSlot;

public class BukkitEquipmentSlotHelper {

    public static org.bukkit.inventory.EquipmentSlot toBukkitEquipmentSlot(EsEquipmentSlot slot) {
        switch (slot) {
            case Feet:
                return org.bukkit.inventory.EquipmentSlot.FEET;
            case Hand:
                return org.bukkit.inventory.EquipmentSlot.HAND;
            case Head:
                return org.bukkit.inventory.EquipmentSlot.HEAD;
            case Legs:
                return org.bukkit.inventory.EquipmentSlot.LEGS;
            case Chest:
                return org.bukkit.inventory.EquipmentSlot.CHEST;
            case OffHand:
                return org.bukkit.inventory.EquipmentSlot.OFF_HAND;
        }

        throw new RuntimeException("idfk");
    }

    public static EsEquipmentSlot fromBukkitEquipmentSlot(org.bukkit.inventory.EquipmentSlot slot) {
        switch (slot) {
            case FEET:
                return EsEquipmentSlot.Feet;
            case HAND:
                return EsEquipmentSlot.Hand;
            case HEAD:
                return EsEquipmentSlot.Head;
            case LEGS:
                return EsEquipmentSlot.Legs;
            case CHEST:
                return EsEquipmentSlot.Chest;
            case OFF_HAND:
                return EsEquipmentSlot.OffHand;
            default:
                throw new IllegalArgumentException("Invalid EquipmentSlot");
        }
    }
}
