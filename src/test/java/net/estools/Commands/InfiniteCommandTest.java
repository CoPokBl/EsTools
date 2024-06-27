package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.ServerApi.EsEquipmentSlot;
import net.estools.ServerApi.Events.EsBlockPlaceEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InfiniteCommandTest extends EsToolsUnitTest {

    @Test
    public void place() {
        executeAssertSuccess("infinite");

        TestItemStack hand = genTestItem();
        hand.setAmount(5);
        player.setMainHand(hand);

        EsBlockPlaceEvent placeEvent = new EsBlockPlaceEvent(player, hand, EsEquipmentSlot.Hand);
        triggerEvent(placeEvent);

        hand.setAmount(4);  // The block is used
        tickTime();  // Trigger the run event to make infinite replenish the block

        Assertions.assertEquals(5, player.getMainHand().getAmount());
    }
}
