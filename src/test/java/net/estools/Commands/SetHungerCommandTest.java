package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetHungerCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        String[] feedback = executeCommand("sethunger");
        Assertions.assertEquals(1, feedback.length);
    }

    @Test
    public void setSelf() {
        player.setFoodLevel(20);
        String[] feedback = executeCommand("sethunger 10");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, player.getFoodLevel());
    }

    @Test
    public void invalidTarget() {
        executeAssertError("sethunger 5 invalid");
    }

    @Test
    public void invalidNumber() {
        executeAssertError("sethunger invalid");
    }

    @Test
    public void setOther() {
        TestPlayer p2 = createPlayer();
        p2.setFoodLevel(20);
        String[] feedback = executeCommand("sethunger", "10", p2.getName());
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertEquals(10, p2.getFoodLevel());
    }
}
