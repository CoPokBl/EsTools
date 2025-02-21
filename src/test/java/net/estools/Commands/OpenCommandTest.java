package net.estools.Commands;

import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Events.EsInventoryCloseEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class OpenCommandTest extends EsToolsCommandTest {

    @Test
    public void openNoArgs() {
        executeAssertOneError("open");
    }

    @Test
    public void openInventory() {
        player.closeInventory();
        executeAssertSuccess("open chest");
        Assertions.assertNotNull(player.getOpenInventory());

        // add dirt to chest, close inventory, now the player should have dirt in inventory
        player.getOpenInventory().addItem(new TestItemStack(EsMaterial.fromKey("dirt"), 1));

        triggerEvent(new EsInventoryCloseEvent(player, player.getOpenInventory()));
        player.closeInventory();

        Assertions.assertTrue(Arrays.stream(player.getInventory().getContents())
                .anyMatch(i -> i != null && i.getType().equals(EsMaterial.fromKey("dirt"))));
    }

    @Test
    public void openInvalidInventory() {
        executeAssertOneError("open invalid");
    }
}
