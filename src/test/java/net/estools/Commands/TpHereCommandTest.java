package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TpHereCommandTest extends EsToolsUnitTest {

    @Test
    public void tpHereMulti() {
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
    public void noArgs() {
        String[] feedback = executeCommand("tphere");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void invalidTarget() {
        executeAssertError("tphere invalid");
    }
}
