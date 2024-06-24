package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import net.serble.estools.ServerApi.Events.EsEntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuddhaCommandTest extends EsToolsUnitTest {

    @Test
    public void buddhaSelf() {
        executeCommand("buddha");

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(player, 50);
        triggerEvent(damageEvent);
        Assertions.assertEquals(0, damageEvent.getDamage());  // Assert cancel damage
    }

    @Test
    public void buddhaOther() {
        TestPlayer p2 = createPlayer();

        executeCommand("buddha", p2.getName());

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(p2, 50);
        triggerEvent(damageEvent);
        Assertions.assertEquals(0, damageEvent.getDamage());  // Assert cancel damage
    }

    @Test
    public void invalidTarget() {
        executeAssertError("buddha invalid");
    }

    @Test
    public void buddhaTimed() {
        initEnvironment();
        TestPlayer p2 = createPlayer();

        executeCommand("buddha", p2.getName(), "20");  // God for 20 seconds

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(p2, 50);
        triggerEvent(damageEvent);
        Assertions.assertEquals(0, damageEvent.getDamage());  // Assert that it cancelled the damage

        tickTime();  // End buddha

        damageEvent = new EsEntityDamageEvent(p2, 50);
        triggerEvent(damageEvent);
        Assertions.assertEquals(50, damageEvent.getDamage());  // Assert no change
    }
}
