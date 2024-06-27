package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /mount <ridee> [rider1] [rider2]...
public class MountCommandTest extends EsToolsUnitTest {

    @Test
    public void noArg() {
        executeAssertOneError("mount");
    }

    @Test
    public void invalidArg() {
        executeAssertOneError("mount invalid");
    }

    @Test
    public void mountSelf() {
        executeAssertOneError("mount " + player.getName());
    }

    @Test
    public void selfMountOther() {
        TestPlayer p2 = createPlayer();
        p2.getPassengers().clear();
        executeAssertSuccess("mount " + p2.getName());

        Assertions.assertTrue(p2.getPassengers().contains(player));
    }

    @Test
    public void targetRideTarget() {
        TestPlayer ridee = createPlayer();
        TestPlayer rider = createPlayer();

        executeAssertSuccess("mount " + ridee.getName() + " " + rider.getName());

        Assertions.assertTrue(ridee.getPassengers().contains(rider));
    }
}
