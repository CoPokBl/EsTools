package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvSeeCommandTest extends EsToolsUnitTest {

    @Test
    public void noArg() {
        executeAssertOneError("invsee");
    }

    @Test
    public void invalidArg() {
        executeAssertOneError("invsee invalid");
    }

    @Test
    public void openInv() {
        TestPlayer p2 = createPlayer();
        executeCommand("invsee", p2.getName());
        Assertions.assertEquals(p2.getInventory(), player.getOpenInventory());
    }
}
