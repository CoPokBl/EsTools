package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TpAllCommandTest extends EsToolsUnitTest {

    @Test
    public void tpAll() {
        TestPlayer p1 = createPlayer();
        TestPlayer p2 = createPlayer();
        TestPlayer p3 = createPlayer();

        p1.teleportRandom();
        p2.teleportRandom();
        p3.teleportRandom();

        executeCommand("tpall");

        Assertions.assertEquals(p1.getLocation(), player.getLocation());
        Assertions.assertEquals(p2.getLocation(), player.getLocation());
        Assertions.assertEquals(p3.getLocation(), player.getLocation());
    }

    @Test
    public void tpAllWithArgs() {
        TestPlayer p1 = createPlayer();
        TestPlayer p2 = createPlayer();
        TestPlayer p3 = createPlayer();

        p1.teleportRandom();
        p2.teleportRandom();
        p3.teleportRandom();

        executeCommand("tpall", player.getName());

        Assertions.assertEquals(p1.getLocation(), player.getLocation());
        Assertions.assertEquals(p2.getLocation(), player.getLocation());
        Assertions.assertEquals(p3.getLocation(), player.getLocation());
    }

    @Test
    public void invalidTarget() {
        executeAssertError("tpall invalid");
    }
}
