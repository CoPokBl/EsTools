package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestPlayer;
import net.serble.estools.ServerApi.Events.EsEntityDamageEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityStatusCommandsTest extends EsToolsUnitTest {

    @Test
    public void heal() {
        TestPlayer p2 = createPlayer();

        player.setHealth(1);
        p2.setHealth(1);

        executeCommand("heal");
        executeCommand("heal", p2.getName());

        Assertions.assertEquals(player.getHealth(), player.getMaxHealth());
        Assertions.assertEquals(p2.getHealth(), p2.getMaxHealth());
    }

    @Test
    public void setHealth() {
        TestPlayer p2 = createPlayer();

        player.setHealth(1);
        p2.setHealth(1);

        executeCommand("sethealth 10");
        executeCommand("sethealth", "10", p2.getName());

        Assertions.assertEquals(player.getHealth(), 10);
        Assertions.assertEquals(p2.getHealth(), 10);
    }

    @Test
    public void feed() {
        TestPlayer p2 = createPlayer();

        player.setFoodLevel(1);
        p2.setFoodLevel(1);

        executeCommand("feed");
        executeCommand("feed", p2.getName());

        Assertions.assertEquals(player.getFoodLevel(), 20);
        Assertions.assertEquals(p2.getFoodLevel(), 20);
    }

    @Test
    public void setFoodLevel() {
        TestPlayer p2 = createPlayer();

        player.setFoodLevel(1);
        p2.setFoodLevel(1);

        executeCommand("sethunger 10");
        executeCommand("sethunger", "10", p2.getName());

        Assertions.assertEquals(player.getFoodLevel(), 10);
        Assertions.assertEquals(p2.getFoodLevel(), 10);
    }

    @Test
    public void setSaturation() {
        TestPlayer p2 = createPlayer();

        player.setSaturation(1);
        p2.setSaturation(1);

        executeCommand("setsaturation 10");
        executeCommand("setsaturation", "10", p2.getName());

        Assertions.assertEquals(player.getSaturation(), 10);
        Assertions.assertEquals(p2.getSaturation(), 10);
    }

    @Test
    public void setMaxHealth() {
        TestPlayer p2 = createPlayer();

        player.setMaxHealth(10);
        p2.setMaxHealth(10);

        executeCommand("setmaxhealth 20");
        executeCommand("setmaxhealth", "20", p2.getName());

        Assertions.assertEquals(player.getMaxHealth(), 20);
        Assertions.assertEquals(p2.getMaxHealth(), 20);
    }

    @Test
    public void god() {
        TestPlayer p2 = createPlayer();

        executeCommand("god");
        executeCommand("god", p2.getName());

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(player, 1);
        triggerEvent(damageEvent);
        Assertions.assertTrue(damageEvent.isCancelled());

        damageEvent = new EsEntityDamageEvent(p2, 1);
        triggerEvent(damageEvent);
        Assertions.assertTrue(damageEvent.isCancelled());
    }

    @Test
    public void buddha() {
        TestPlayer p2 = createPlayer();

        player.setHealth(20);
        p2.setHealth(20);

        executeCommand("buddha");
        executeCommand("buddha", p2.getName());

        EsEntityDamageEvent damageEvent = new EsEntityDamageEvent(player, 1);
        triggerEvent(damageEvent);
        Assertions.assertFalse(damageEvent.isCancelled());

        damageEvent = new EsEntityDamageEvent(p2, 50);
        triggerEvent(damageEvent);
        Assertions.assertNotEquals(damageEvent.getDamage(), 50);
    }
}
