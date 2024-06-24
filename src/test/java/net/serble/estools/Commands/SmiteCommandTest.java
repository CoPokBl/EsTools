package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmiteCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertError("smite");
    }

    @Test
    public void self() {
        world.getLightningBolts().clear();
        executeCommand("smite", player.getName());
        Assertions.assertEquals(1, world.getLightningBolts().size());
    }

    @Test
    public void multiple() {
        world.getLightningBolts().clear();
        TestPlayer p2 = createPlayer();
        executeCommand("smite", player.getName(), p2.getName());
        Assertions.assertEquals(2, world.getLightningBolts().size());
    }

    @Test
    public void invalidTarget() {
        executeAssertError("smite invalid");
    }
}
