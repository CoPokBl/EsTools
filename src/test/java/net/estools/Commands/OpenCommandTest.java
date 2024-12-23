package net.estools.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void openInvalidInventory() {
        executeAssertOneError("open invalid");
    }
}
