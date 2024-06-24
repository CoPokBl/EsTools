package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Test;

public class GetInfoCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertError("getinfo");
    }

    @Test
    public void invalidTarget() {
        executeAssertError("getinfo invalid");
    }

    @Test
    public void validTarget() {
        executeAssertSuccess("getinfo " + player.getName());
    }
}
