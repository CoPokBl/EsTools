package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetMaxHealthCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        String[] feedback = executeCommand("setmaxhealth");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void setSelf() {
        player.setMaxHealth(20);
        String[] feedback = executeCommand("setmaxhealth 10");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, player.getMaxHealth());
    }

    @Test
    public void invalidTarget() {
        executeAssertOneError("setmaxhealth 5 invalid");
    }

    @Test
    public void invalidNumber() {
        executeAssertOneError("setmaxhealth invalid");
    }

    @Test
    public void setOther() {
        TestPlayer p2 = createPlayer();
        p2.setMaxHealth(20);
        String[] feedback = executeCommand("setmaxhealth", "10", p2.getName());
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, p2.getMaxHealth());
    }
}
