package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import net.estools.Implementation.TestPlayer;
import net.estools.Implementation.TestPotion;
import net.estools.ServerApi.EsPotType;
import net.estools.ServerApi.EsPotionEffectType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /potion <effect> [amplifier] [duration] [amount] [drink/splash/lingering] [player]
public class PotionCommandTest extends EsToolsUnitTest {

    @Test
    public void noArg() {
        executeAssertOneError("potion");
    }

    @Test
    public void invalidEffect() {
        executeAssertOneError("potion invalid");
    }

    @Test
    public void createPot() {
        player.getInventory().clear();
        executeAssertSuccess("potion speed");
        TestItemStack slot0 = (TestItemStack) player.getInventory().getItem(0);
        Assertions.assertInstanceOf(TestPotion.class, slot0);

        TestPotion pot = (TestPotion) slot0;

        Assertions.assertEquals(EsPotType.drink, pot.getPotionType());
        Assertions.assertEquals(pot.getEffects()[0].getType(), EsPotionEffectType.fromKey("speed"));
    }

    @Test
    public void validAmp() {
        player.getInventory().clear();
        executeAssertSuccess("potion speed 2");
        TestItemStack slot0 = (TestItemStack) player.getInventory().getItem(0);
        Assertions.assertInstanceOf(TestPotion.class, slot0);

        TestPotion pot = (TestPotion) slot0;

        Assertions.assertEquals(EsPotType.drink, pot.getPotionType());
        Assertions.assertEquals(pot.getEffects()[0].getType(), EsPotionEffectType.fromKey("speed"));
        Assertions.assertEquals(1, pot.getEffects()[0].getAmp());
    }

    @Test
    public void validDuration() {
        player.getInventory().clear();
        executeAssertSuccess("potion speed 2 5");
        TestItemStack slot0 = (TestItemStack) player.getInventory().getItem(0);
        Assertions.assertInstanceOf(TestPotion.class, slot0);

        TestPotion pot = (TestPotion) slot0;

        Assertions.assertEquals(EsPotType.drink, pot.getPotionType());
        Assertions.assertEquals(pot.getEffects()[0].getType(), EsPotionEffectType.fromKey("speed"));
        Assertions.assertEquals(1, pot.getEffects()[0].getAmp());
        Assertions.assertEquals(5*20, pot.getEffects()[0].getDuration());
    }

    @Test
    public void validAmount() {
        player.getInventory().clear();
        executeAssertSuccess("potion speed 2 5 69");
        TestItemStack slot0 = (TestItemStack) player.getInventory().getItem(0);
        Assertions.assertInstanceOf(TestPotion.class, slot0);

        TestPotion pot = (TestPotion) slot0;

        Assertions.assertEquals(EsPotType.drink, pot.getPotionType());
        Assertions.assertEquals(pot.getEffects()[0].getType(), EsPotionEffectType.fromKey("speed"));
        Assertions.assertEquals(1, pot.getEffects()[0].getAmp());
        Assertions.assertEquals(5*20, pot.getEffects()[0].getDuration());
        Assertions.assertEquals(69, pot.getAmount());
    }

    @Test
    public void validType() {
        player.getInventory().clear();
        executeAssertSuccess("potion speed 2 5 69 splash");
        TestItemStack slot0 = (TestItemStack) player.getInventory().getItem(0);
        Assertions.assertInstanceOf(TestPotion.class, slot0);

        TestPotion pot = (TestPotion) slot0;

        Assertions.assertEquals(EsPotType.splash, pot.getPotionType());
        Assertions.assertEquals(pot.getEffects()[0].getType(), EsPotionEffectType.fromKey("speed"));
        Assertions.assertEquals(1, pot.getEffects()[0].getAmp());
        Assertions.assertEquals(5*20, pot.getEffects()[0].getDuration());
        Assertions.assertEquals(69, pot.getAmount());
    }

    @Test
    public void anotherPlayer() {
        TestPlayer p = createPlayer();

        p.getInventory().clear();
        executeAssertSuccess("potion speed 2 5 69 splash " + p.getName());
        TestItemStack slot0 = (TestItemStack) p.getInventory().getItem(0);
        Assertions.assertInstanceOf(TestPotion.class, slot0);

        TestPotion pot = (TestPotion) slot0;

        Assertions.assertEquals(EsPotType.splash, pot.getPotionType());
        Assertions.assertEquals(pot.getEffects()[0].getType(), EsPotionEffectType.fromKey("speed"));
        Assertions.assertEquals(1, pot.getEffects()[0].getAmp());
        Assertions.assertEquals(5*20, pot.getEffects()[0].getDuration());
        Assertions.assertEquals(69, pot.getAmount());
    }
}
