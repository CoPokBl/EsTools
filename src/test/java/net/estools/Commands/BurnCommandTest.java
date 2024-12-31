package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BurnCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {  // Needs to run first
        executeAssertOneError("burn");
    }

    @Test
    public void defaultTicks() {
        executeAssertSuccess("burn " + playerName);
        Assertions.assertEquals(100, player.getOnFireTicks());
    }

    @Test
    public void customTicks() {
        executeAssertSuccess("burn " + playerName + " 200");
        Assertions.assertEquals(200, player.getOnFireTicks());
    }

    @Test
    public void invalidEntity() {
        executeAssertOneError("burn invalid");
    }

    @Test
    public void invalidTicks() {
        executeAssertOneError("burn " + playerName + " invalid");
    }
}
