package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /dismount [rider1] [rider2]...
public class DismountCommandTest extends EsToolsUnitTest {

    @Test
    public void noArg() {
        executeAssertOneError("dismount");
    }

    @Test
    public void invalidArg() {
        executeAssertOneError("dismount invalid");
    }

    @Test
    public void dismountFromSelf() {
        TestPlayer p2 = createPlayer();
        p2.getPassengers().clear();
        player.addPassenger(p2);
        executeAssertSuccess("dismount " + p2.getName());

        Assertions.assertFalse(player.getPassengers().contains(p2));
    }

    @Test
    public void dismountSelf() {
        TestPlayer ridee = createPlayer();
        ridee.addPassenger(player);

        executeAssertSuccess("dismount");

        Assertions.assertFalse(ridee.getPassengers().contains(player));
    }
}
