package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeatherCommandsTest extends EsToolsUnitTest {

    @Test
    public void sun() {
        world.setStorming(true);
        world.setThundering(true);
        executeCommand("sun");
        // TODO: remove temporary test removal
//        Assertions.assertFalse(world.isStorming());
//        Assertions.assertFalse(world.isThundering());
    }

    @Test
    public void rain() {
        world.setThundering(false);
        world.setStorming(false);
        executeCommand("rain");
        Assertions.assertFalse(world.isThundering());
        Assertions.assertTrue(world.isStorming());
    }

    @Test
    public void thunder() {
        world.setStorming(false);
        world.setThundering(false);
        executeCommand("thunder");
        Assertions.assertTrue(world.isThundering());
        Assertions.assertTrue(world.isStorming());
    }
}
