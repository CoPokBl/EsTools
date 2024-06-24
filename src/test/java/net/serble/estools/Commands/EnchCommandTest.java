package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestItemStack;
import net.serble.estools.ServerApi.EsEnchantment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnchCommandTest extends EsToolsUnitTest {

    @Test
    public void noItem() {
        player.setMainHand(null);
        executeAssertError("ench");
    }

    @Test
    public void enchantItem() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeAssertSuccess("ench sharpness 1");
        Assertions.assertEquals(1, item.getEnchantments().get(EsEnchantment.createUnchecked("sharpness")));
    }

    @Test
    public void invalidEnchant() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeAssertError("ench invalid");
        Assertions.assertNull(item.getEnchantments().get(EsEnchantment.createUnchecked("invalid")));
    }

    @Test
    public void invalidLevel() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeAssertError("ench sharpness invalid");
        Assertions.assertNull(item.getEnchantments().get(EsEnchantment.createUnchecked("sharpness")));
    }
}
