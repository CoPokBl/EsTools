package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FeedCommandTest extends EsToolsUnitTest {

    @Test
    public void feedSelf() {
        player.setFoodLevel(1);
        executeCommand("feed");
        Assertions.assertEquals(20, player.getFoodLevel());
    }

    @Test
    public void feedOther() {
        TestPlayer p2 = createPlayer();
        p2.setFoodLevel(1);
        executeCommand("feed", p2.getName());
        Assertions.assertEquals(20, player.getFoodLevel());
    }

    @Test
    public void invalidTarget() {
        executeAssertError("feed invalid");
    }

    @Test
    public void feedOtherUuid() {
        TestPlayer p2 = createPlayer();
        p2.setFoodLevel(1);
        executeCommand("feed", p2.getUniqueId().toString());
        Assertions.assertEquals(20, player.getFoodLevel());
    }
}
