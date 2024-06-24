package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetSaturationCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        String[] feedback = executeCommand("setsaturation");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void setSelf() {
        player.setSaturation(20);
        String[] feedback = executeCommand("setsaturation 10");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, player.getSaturation());
    }

    @Test
    public void invalidTarget() {
        executeAssertError("setsaturation 5 invalid");
    }

    @Test
    public void invalidNumber() {
        executeAssertError("setsaturation invalid");
    }

    @Test
    public void setOther() {
        TestPlayer p2 = createPlayer();
        p2.setSaturation(20);
        String[] feedback = executeCommand("setsaturation", "10", p2.getName());
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, p2.getSaturation());
    }
}
