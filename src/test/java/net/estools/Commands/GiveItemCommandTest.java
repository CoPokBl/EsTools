package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsMaterial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GiveItemCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("i");
    }

    @Test
    public void invalidItem() {
        executeAssertOneError("i invalid");
    }

    @Test
    public void giveItem() {
        player.getInventory().clear();
        world.getEntities().clear();
        executeAssertSuccess("i dirt");
        Assertions.assertEquals(EsMaterial.fromKey("dirt"), player.getInventory().getItem(0).getType());

        // make sure it didn't drop the item
        Assertions.assertFalse(world.getEntities().stream().anyMatch(entity -> entity.getType().equals("ITEM")));
    }

    @Test
    public void invalidAmount() {
        executeAssertOneError("i dirt invalid");
    }

    @Test
    public void giveItemAmount() {
        player.getInventory().clear();
        world.getEntities().clear();
        executeAssertSuccess("i dirt 69");
        Assertions.assertEquals(EsMaterial.fromKey("dirt"), player.getInventory().getItem(0).getType());
        Assertions.assertEquals(69, player.getInventory().getItem(0).getAmount());

        // make sure it didn't drop the item
        Assertions.assertFalse(world.getEntities().stream().anyMatch(entity -> entity.getType().equals("ITEM")));
    }

    @Test
    public void giveItemDrop() {
        player.getInventory().clear();
        world.getEntities().clear();

        // fill inventory
        for (int i = 0; i < 54; i++) {
            player.getInventory().addItem(new TestItemStack(EsMaterial.fromKey("dirt"), 64));
        }

        // add item, see if it dropped item
        executeAssertSuccess("i dirt");
        Assertions.assertTrue(world.getEntities().stream().anyMatch(entity -> entity.getType().equals("ITEM")));
    }
}
