package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.ServerApi.EsTeleportCause;
import net.estools.ServerApi.Events.EsPlayerTeleportEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SafeTpCommandTest extends EsToolsUnitTest {

    @Test
    public void safetp() {
        SafeTp.enabled = false;
        executeAssertSuccess("safetp");
        player.setFallDistance(10);

        EsPlayerTeleportEvent teleportEvent = new EsPlayerTeleportEvent(player, EsTeleportCause.Command, null);
        triggerEvent(teleportEvent);  // This should cause SafeTP to reset fall distance

        Assertions.assertEquals(0, player.getFallDistance());
    }
}
