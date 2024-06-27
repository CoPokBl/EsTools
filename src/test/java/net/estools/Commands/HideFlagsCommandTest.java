package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemMeta;
import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsItemFlag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HideFlagsCommandTest extends EsToolsUnitTest {

    @Test
    public void noItem() {
        player.setMainHand(null);
        executeAssertOneError("hideflags");
    }

    @Test
    public void hideFlags() {
        TestItemStack hand = genTestItem();
        player.setMainHand(hand);
        executeAssertSuccess("hideflags");
        Assertions.assertEquals(EsItemFlag.values().length, hand.getItemMeta().getItemFlags().size());
    }

    @Test
    public void showFlags() {
        TestItemStack hand = genTestItem();
        TestItemMeta meta = hand.getItemMeta();
        meta.addItemFlags(EsItemFlag.values());
        hand.setItemMeta(meta);
        player.setMainHand(hand);
        executeAssertSuccess("hideflags");
        Assertions.assertEquals(0, hand.getItemMeta().getItemFlags().size());
    }
}
