package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.ServerApi.EsTeleportCause;
import net.estools.ServerApi.Events.EsPlayerDeathEvent;
import net.estools.ServerApi.Events.EsPlayerTeleportEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BackCommandTest extends EsToolsUnitTest {

    @Test
    public void noLastLocation() {  // Needs to run first
        executeAssertOneError("back");
    }

    @Test
    public void deathBack() {
        go(12, 60, 12);

        EsPlayerDeathEvent deathEvent = new EsPlayerDeathEvent(player);
        triggerEvent(deathEvent);

        go(0, 60, 0);

        executeAssertSuccess("back");
        Assertions.assertEquals(loc(12, 60, 12), player.getLocation());
    }

    @Test
    public void tpBack() {
        go(12, 60, 12);

        EsPlayerTeleportEvent tpEvent = new EsPlayerTeleportEvent(player, EsTeleportCause.Command, loc(0, 60, 0));
        triggerEvent(tpEvent);

        go(0, 60, 0);

        executeAssertSuccess("back");
        Assertions.assertEquals(loc(12, 60, 12), player.getLocation());
    }
}
