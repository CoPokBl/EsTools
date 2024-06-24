package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsEquipmentSlot;
import net.estools.ServerApi.Interfaces.EsItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FixCommandTest extends EsToolsUnitTest {

    @Test
    public void noItem() {
        player.setMainHand(null);
        executeCommand("fix");  // Just check if it throws
    }

    @Test
    public void fixHand() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.setMainHand(item);
        executeAssertSuccess("fix");
        Assertions.assertEquals(0, item.getDamage());
    }

    @Test
    public void fixOffhand() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.getInventory().setItem(EsEquipmentSlot.OffHand, item);
        executeAssertSuccess("fix offhand");
        Assertions.assertEquals(0, item.getDamage());
    }

    @Test
    public void fixHelmet() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.getInventory().setItem(EsEquipmentSlot.Head, item);
        executeAssertSuccess("fix helmet");
        Assertions.assertEquals(0, item.getDamage());
    }

    @Test
    public void fixChestplate() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.getInventory().setItem(EsEquipmentSlot.Chest, item);
        executeAssertSuccess("fix chestplate");
        Assertions.assertEquals(0, item.getDamage());
    }

    @Test
    public void fixLeggings() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.getInventory().setItem(EsEquipmentSlot.Legs, item);
        executeAssertSuccess("fix leggings");
        Assertions.assertEquals(0, item.getDamage());
    }

    @Test
    public void fixBoots() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.getInventory().setItem(EsEquipmentSlot.Feet, item);
        executeAssertSuccess("fix boots");
        Assertions.assertEquals(0, item.getDamage());
    }

    @Test
    public void fixAll() {
        TestItemStack item = genTestItem();
        item.setDamage(10);
        player.setMainHand(item);
        player.getInventory().setItem(EsEquipmentSlot.OffHand, item.clone());
        player.getInventory().setItem(EsEquipmentSlot.Head, item.clone());
        player.getInventory().setItem(EsEquipmentSlot.Chest, item.clone());
        player.getInventory().setItem(EsEquipmentSlot.Legs, item.clone());
        player.getInventory().setItem(EsEquipmentSlot.Feet, item.clone());
        player.getInventory().setItem(2, item.clone());
        executeAssertSuccess("fix all");
        Assertions.assertEquals(0, item.getDamage());
        for (EsItemStack i : player.getInventory().getContents()) {
            if (i != null) {
                Assertions.assertEquals(0, i.getDamage());
            }
        }
    }

    @Test
    public void invalidSlot() {
        executeAssertError("fix invalid");
    }
}
