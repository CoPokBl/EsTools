package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Test;

public class GetInfoCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("getinfo");
    }

    @Test
    public void validTarget() {
        executeAssertSuccess("getinfo " + player.getName());
    }
}
