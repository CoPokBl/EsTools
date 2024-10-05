package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import net.estools.ServerApi.EsLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WarpCommandTest extends EsToolsUnitTest {

    @Test
    public void warpNoArgs() {
        executeAssertOneError("warp");
    }

    @Test
    public void warpsNoArgs() {
        executeAssertOneError("warps");
    }

    @Test
    public void invalidWarp() {
        executeAssertOneError("warp invalid");
    }

    @Test
    public void createWarpNoPosition() {
        go(69, 69, 69);
        executeAssertSuccess("warps add nopos");
        go(0, 60, 0);
        executeAssertSuccess("warp nopos");
        Assertions.assertEquals(loc(69, 69, 69), player.getLocation());
    }

    @Test
    public void removeWarp() {
        executeAssertSuccess("warps add removewarp");
        executeAssertSuccess("warps remove removewarp");
        executeAssertOneError("warp removewarp");
    }

    @Test
    public void createWarpWithPosition() {
        go(0, 60, 0);
        executeAssertSuccess("warps add withpos local 69 69 69");
        executeAssertSuccess("warp withpos");
        Assertions.assertEquals(loc(69, 69, 69), player.getLocation());
    }

    @Test
    public void warpTarget() {
        go(0, 60, 0);
        executeAssertSuccess("warps add targetwarp local 69 69 69");
        executeAssertSuccess("warp targetwarp " + playerName);
        Assertions.assertEquals(loc(69, 69, 69), player.getLocation());
    }

    @Test
    public void warpTargets() {
        TestPlayer p2 = createPlayer();
        TestPlayer p3 = createPlayer();

        go(0, 60, 0);
        EsLocation loc = loc(0, 60, 0);
        p2.teleport(loc);
        p3.teleport(loc);

        executeAssertSuccess("warps add targetswarp local 69 69 69");
        executeAssertSuccess("warp targetswarp " + playerName + " " + p2.getName() + " " + p3.getName());
        Assertions.assertEquals(loc(69, 69, 69), player.getLocation());
        Assertions.assertEquals(loc(69, 69, 69), p2.getLocation());
        Assertions.assertEquals(loc(69, 69, 69), p3.getLocation());
    }
}
