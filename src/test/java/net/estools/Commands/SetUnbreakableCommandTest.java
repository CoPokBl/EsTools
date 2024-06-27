package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemMeta;
import net.estools.Implementation.TestItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetUnbreakableCommandTest extends EsToolsUnitTest {

    @Test
    public void noHeldItem() {
        player.setMainHand(null);
        executeAssertOneError("setunbreakable");
    }

    @Test
    public void setUnbreakable() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeAssertSuccess("setunbreakable");
        Assertions.assertTrue(item.getItemMeta().isUnbreakable());
    }

    @Test
    public void setBreakable() {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);

        player.setMainHand(item);
        executeAssertSuccess("setunbreakable");
        Assertions.assertFalse(item.getItemMeta().isUnbreakable());
    }
}
