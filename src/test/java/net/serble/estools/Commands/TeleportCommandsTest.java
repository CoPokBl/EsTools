package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeleportCommandsTest extends EsToolsUnitTest {

    @Test
    public void tpHere() {
        TestPlayer p1 = createPlayer();
        TestPlayer p2 = createPlayer();
        TestPlayer p3 = createPlayer();

        p1.teleportRandom();
        p2.teleportRandom();
        p3.teleportRandom();

        executeCommand("tphere", p1.getName(), p2.getName());

        Assertions.assertEquals(p1.getLocation(), player.getLocation());
        Assertions.assertEquals(p2.getLocation(), player.getLocation());
        Assertions.assertNotEquals(p3.getLocation(), player.getLocation());
    }

    @Test
    public void tpAll() {
        TestPlayer p1 = createPlayer();
        TestPlayer p2 = createPlayer();
        TestPlayer p3 = createPlayer();

        p1.teleportRandom();
        p2.teleportRandom();
        p3.teleportRandom();

        executeCommand("tpall", p1.getName());

        // Is everyone at p1?
        Assertions.assertEquals(player.getLocation(), p1.getLocation());
        Assertions.assertEquals(p2.getLocation(), p1.getLocation());
        Assertions.assertEquals(p3.getLocation(), p1.getLocation());
    }
}
