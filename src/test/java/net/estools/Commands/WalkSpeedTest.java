package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalkSpeedTest extends EsToolsUnitTest {

    @Test
    public void noArg() {
        String[] feedback = executeCommand("walkspeed");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void setSelf() {
        player.setWalkSpeed(2);
        executeCommand("walkspeed 5");
        Assertions.assertEquals(0.5, player.getWalkSpeed());
    }

    @Test
    public void setOther() {
        TestPlayer p2 = createPlayer();
        p2.setWalkSpeed(2);
        executeCommand("walkspeed", "5", p2.getName());
        Assertions.assertEquals(0.5, p2.getWalkSpeed());
    }

    @Test
    public void invalidTarget() {
        executeAssertError("walkspeed 5 invalid");
    }

    @Test
    public void invalidNumber() {
        executeAssertError("walkspeed invalid");
    }
}
