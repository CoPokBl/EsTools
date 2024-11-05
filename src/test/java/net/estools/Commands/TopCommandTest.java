package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import net.estools.ServerApi.EsLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TopCommandTest extends EsToolsUnitTest {

    @Test
    public void noBlocks() {
        resetWorld();
        go(0, 0, 0);
        executeAssertError("top");
    }

    @Test
    public void above() {
        resetWorld();
        go(20, 60, 20);
        world.addBlock(20, 90, 20);
        executeAssertSuccess("top");
        Assertions.assertEquals(91, player.getLocation().getBlockY());
    }

    @Test
    public void below() {
        resetWorld();
        go(-20, 60, -20);
        world.addBlock(-20, 20, -20);
        executeAssertSuccess("top");
        Assertions.assertEquals(21, player.getLocation().getBlockY());
    }

    @Test
    public void withPlayer() {
        resetWorld();
        TestPlayer p1 = createPlayer();
        p1.teleport(new EsLocation(world, 20, 60 ,20));
        world.addBlock(20, 80, 20);
        executeAssertSuccess("top " + p1.getName());
        Assertions.assertEquals(81, p1.getLocation().getBlockY());
    }

    @Test
    public void invalidPlayer() {
        executeAssertOneError("top invalid");
    }
}
