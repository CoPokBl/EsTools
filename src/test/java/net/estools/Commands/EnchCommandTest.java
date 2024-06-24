package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsEnchantment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnchCommandTest extends EsToolsUnitTest {

    @Test
    public void noItem() {
        player.setMainHand(null);
        executeAssertOneError("ench");
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
        executeAssertOneError("ench invalid");
        Assertions.assertNull(item.getEnchantments().get(EsEnchantment.createUnchecked("invalid")));
    }

    @Test
    public void invalidLevel() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeAssertOneError("ench sharpness invalid");
        Assertions.assertNull(item.getEnchantments().get(EsEnchantment.createUnchecked("sharpness")));
    }
}
