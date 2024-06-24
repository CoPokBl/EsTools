package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestItemStack;
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

    // TODO: Fix inv, fix other slots
}
