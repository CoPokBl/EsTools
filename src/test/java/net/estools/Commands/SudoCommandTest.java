package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SudoCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("sudo");
    }

    @Test
    public void onlyUser() {
        executeAssertOneError("sudo " + player.getName());
    }

    @Test
    public void invalidUser() {
        executeAssertOneError("sudo invalid some command");
    }

    @Test
    public void sudoUser() {
        TestPlayer p2 = createPlayer();
        pendingCommands.clear();
        executeCommand("sudo", p2.getName(), "some command");
        Assertions.assertEquals(1, pendingCommands.size());
        Assertions.assertEquals("some command", pendingCommands.get(p2));
    }
}
