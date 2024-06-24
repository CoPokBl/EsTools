package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SuicideCommandTest extends EsToolsUnitTest {

    @Test
    public void suicide() {
        executeCommand("suicide");
        Assertions.assertEquals(player.getHealth(), 0);
    }
}
