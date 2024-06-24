package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.ServerApi.EsMaterial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetHandCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("h");
    }

    @Test
    public void invalidItem() {
        executeAssertOneError("h invalid");
    }

    @Test
    public void giveItem() {
        player.getInventory().clear();
        player.setSelectedSlot(5);
        executeAssertSuccess("h dirt");
        Assertions.assertEquals(EsMaterial.fromKey("dirt"), player.getInventory().getItem(5).getType());
    }

    @Test
    public void invalidAmount() {
        executeAssertOneError("h dirt invalid");
    }

    @Test
    public void giveItemAmount() {
        player.getInventory().clear();
        player.setSelectedSlot(5);
        executeAssertSuccess("h dirt 69");
        Assertions.assertEquals(EsMaterial.fromKey("dirt"), player.getInventory().getItem(5).getType());
        Assertions.assertEquals(69, player.getInventory().getItem(5).getAmount());
    }
}
