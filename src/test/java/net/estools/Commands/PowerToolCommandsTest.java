package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PowerToolCommandsTest extends EsToolsUnitTest {

    private void testTool(String cmd) {
        player.getInventory().clear();
        executeAssertSuccess(cmd);
        Assertions.assertNotNull(player.getInventory().getItem(0));
    }

    @Test
    public void axe() {
        testTool("poweraxe");
    }

    @Test
    public void hoe() {
        testTool("powerhoe");
    }

    @Test
    public void pick() {
        testTool("powerpick");
    }

    @Test
    public void shovel() {
        testTool("powershovel");
    }

    @Test
    public void sword() {
        testTool("powersword");
    }
}
