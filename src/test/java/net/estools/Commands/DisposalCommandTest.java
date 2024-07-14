package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DisposalCommandTest extends EsToolsUnitTest {

    @Test
    public void deleteItem() {
        // Insert an item
        executeCommand("disposal");
        Assertions.assertNotNull(player.getOpenInventory());
        player.getOpenInventory().setItem(0, genTestItem());
        player.closeInventory();

        // Reopen and check
        executeCommand("disposal");
        Assertions.assertNotNull(player.getOpenInventory());
        Assertions.assertNull(player.getOpenInventory().getItem(0));
    }
}
