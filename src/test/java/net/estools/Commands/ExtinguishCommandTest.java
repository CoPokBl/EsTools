package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExtinguishCommandTest extends EsToolsUnitTest {

    @Test
    public void self() {  // Needs to run first
        player.setOnFireTicks(100);
        executeAssertSuccess("extinguish");
        Assertions.assertEquals(0, player.getOnFireTicks());
    }

    @Test
    public void other() {
        TestPlayer p2 = createPlayer();
        p2.setOnFireTicks(100);
        executeAssertSuccess("extinguish " + p2.getName());
        Assertions.assertEquals(0, p2.getOnFireTicks());
    }

    @Test
    public void invalidEntity() {
        executeAssertOneError("extinguish invalid");
    }
}
