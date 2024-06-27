package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsClickType;
import net.estools.ServerApi.EsInventoryAction;
import net.estools.ServerApi.Events.EsInventoryClickEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CChestCommandTest extends EsToolsUnitTest {

    @Test
    public void placeRemoveItem() {
        executeCommand("cchest");
        Assertions.assertNotNull(player.getOpenInventory());

        TestItemStack item = genTestItem();

        EsInventoryClickEvent clickEvent = new EsInventoryClickEvent(
                player,
                5,
                item,
                player.getOpenInventory(),
                player.getOpenInventory(),
                item,
                EsInventoryAction.PlaceAll,
                EsClickType.Left);
        triggerEvent(clickEvent);
        Assertions.assertTrue(clickEvent.isCancelled());

        player.closeInventory();
        Assertions.assertNull(player.getOpenInventory());

        executeCommand("cchest");

        Assertions.assertNotNull(player.getOpenInventory());

        tickTime();  // It does runTask to modify inv at some point

        // Check if the test item is in the slot, the item is cloned so use isSimilar
        Assertions.assertTrue(item.isSimilar(player.getOpenInventory().getItem(5)));

        // Check if the item is removed
        clickEvent = new EsInventoryClickEvent(
                player,
                5,
                null,
                player.getOpenInventory(),
                player.getOpenInventory(),
                item,
                EsInventoryAction.PickupHalf,
                EsClickType.Right);
        triggerEvent(clickEvent);

        Assertions.assertTrue(clickEvent.isCancelled());
        Assertions.assertNull(player.getOpenInventory().getItem(5));

        player.closeInventory();
    }
}
