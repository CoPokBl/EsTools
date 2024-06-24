package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlySpeedTest extends EsToolsUnitTest {

    @Test
    public void noArg() {
        String[] feedback = executeCommand("flyspeed");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void setSelf() {
        player.setFlySpeed(2);
        executeCommand("flyspeed 5");
        Assertions.assertEquals(0.5, player.getFlySpeed());
    }

    @Test
    public void invalidTarget() {
        executeAssertOneError("flyspeed 5 invalid");
    }

    @Test
    public void invalidNumber() {
        executeAssertOneError("flyspeed invalid");
    }

    @Test
    public void setOther() {
        TestPlayer p2 = createPlayer();
        p2.setFlySpeed(2);
        executeCommand("flyspeed", "5", p2.getName());
        Assertions.assertEquals(0.5, p2.getFlySpeed());
    }
}
