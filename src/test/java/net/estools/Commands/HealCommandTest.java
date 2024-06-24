package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HealCommandTest extends EsToolsUnitTest {

    @Test
    public void healSelf() {
        player.setHealth(1);
        executeCommand("heal");
        Assertions.assertEquals(player.getHealth(), player.getMaxHealth());
    }

    @Test
    public void healOther() {
        TestPlayer p2 = createPlayer();
        p2.setHealth(1);
        executeCommand("heal", p2.getName());
        Assertions.assertEquals(player.getHealth(), player.getMaxHealth());
    }

    @Test
    public void invalidTarget() {
        executeAssertOneError("heal invalid");
    }

    @Test
    public void healOtherUuid() {
        TestPlayer p2 = createPlayer();
        p2.setHealth(1);
        executeCommand("heal", p2.getUniqueId().toString());
        Assertions.assertEquals(player.getHealth(), player.getMaxHealth());
    }
}
