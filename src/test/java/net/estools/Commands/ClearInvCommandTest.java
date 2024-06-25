package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClearInvCommandTest extends EsToolsUnitTest {

    @Test
    public void clearInventorySelf() {
        TestItemStack item = genTestItem();
        for (int i = 0; i < 36; i++) {
            player.getInventory().setItem(i, item);
        }

        executeCommand("ci");

        for (int i = 0; i < 36; i++) {
            Assertions.assertNull(player.getInventory().getItem(i));
        }
    }

    @Test
    public void clearInventoryOther() {
        TestItemStack item = genTestItem();
        TestPlayer other = createPlayer();
        for (int i = 0; i < 36; i++) {
            other.getInventory().setItem(i, item);
        }

        executeCommand("ci", other.getName());

        for (int i = 0; i < 36; i++) {
            Assertions.assertNull(other.getInventory().getItem(i));
        }
    }

    @Test
    public void invalidPlayer() {
        executeAssertOneError("ci invalid");
    }
}
