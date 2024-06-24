package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetHealthCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        String[] feedback = executeCommand("sethealth");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void setSelf() {
        player.setHealth(20);
        String[] feedback = executeCommand("sethealth 10");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, player.getHealth());
    }

    @Test
    public void invalidTarget() {
        executeAssertOneError("sethealth 5 invalid");
    }

    @Test
    public void invalidNumber() {
        executeAssertOneError("sethealth invalid");
    }

    @Test
    public void setOther() {
        TestPlayer p2 = createPlayer();
        p2.setHealth(20);
        String[] feedback = executeCommand("sethealth", "10", p2.getName());
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, p2.getHealth());
    }
}
