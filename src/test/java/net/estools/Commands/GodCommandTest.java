package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import net.estools.ServerApi.Events.EsEntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GodCommandTest extends EsToolsUnitTest {

    @Test
    public void godSelf() {
        executeCommand("god");

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(player, 1);
        triggerEvent(damageEvent);
        Assertions.assertTrue(damageEvent.isCancelled());
    }

    @Test
    public void godOther() {
        TestPlayer p2 = createPlayer();

        executeCommand("god", p2.getName());

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(p2, 1);
        triggerEvent(damageEvent);
        Assertions.assertTrue(damageEvent.isCancelled());
    }

    @Test
    public void invalidTarget() {
        executeAssertOneError("god invalid");
    }

    @Test
    public void godTimed() {
        initEnvironment();
        TestPlayer p2 = createPlayer();

        executeCommand("god", p2.getName(), "20");  // God for 20 seconds

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(p2, 1);
        triggerEvent(damageEvent);
        Assertions.assertTrue(damageEvent.isCancelled());

        tickTime();  // End god

        damageEvent = new EsEntityDamageEvent(p2, 1);
        triggerEvent(damageEvent);
        Assertions.assertFalse(damageEvent.isCancelled());
    }
}
