package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeCommandsTest extends EsToolsUnitTest {

    @Test
    public void day() {
        world.setTime(0);
        executeCommand("day");
        Assertions.assertEquals(1000, world.getTime());
    }

    @Test
    public void night() {
        world.setTime(0);
        executeCommand("night");
        Assertions.assertEquals(13000, world.getTime());
    }

    @Test
    public void midnight() {
        world.setTime(0);
        executeCommand("midnight");
        Assertions.assertEquals(18000, world.getTime());
    }

    @Test
    public void noon() {
        world.setTime(0);
        executeCommand("noon");
        Assertions.assertEquals(6000, world.getTime());
    }
}
