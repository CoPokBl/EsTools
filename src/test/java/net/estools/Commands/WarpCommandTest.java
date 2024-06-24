package net.estools.Commands;

import net.estools.EsToolsUnitTest;
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
}
