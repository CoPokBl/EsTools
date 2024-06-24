package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmiteCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("smite");
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
        executeAssertOneError("smite invalid");
    }
}
