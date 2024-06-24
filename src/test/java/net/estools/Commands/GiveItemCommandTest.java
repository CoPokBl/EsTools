package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.ServerApi.EsMaterial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GiveItemCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("i");
    }

    @Test
    public void invalidItem() {
        executeAssertOneError("i invalid");
    }

    @Test
    public void giveItem() {
        player.getInventory().clear();
        executeAssertSuccess("i dirt");
        Assertions.assertEquals(EsMaterial.fromKey("dirt"), player.getInventory().getItem(0).getType());
    }

    @Test
    public void invalidAmount() {
        executeAssertOneError("i dirt invalid");
    }

    @Test
    public void giveItemAmount() {
        player.getInventory().clear();
        executeAssertSuccess("i dirt 69");
        Assertions.assertEquals(EsMaterial.fromKey("dirt"), player.getInventory().getItem(0).getType());
        Assertions.assertEquals(69, player.getInventory().getItem(0).getAmount());
    }
}
