package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EsToolsCommandTest extends EsToolsUnitTest {

    @Test
    public void displaysVersion() {
        String[] result = executeCommand("estools");
        Assertions.assertTrue(result.length != 0);
        Assertions.assertTrue(stripColour(result[result.length-1]).startsWith("EsTools v"));
    }

    @Test
    public void throwException() {
        executeAssertOneError("estools throwunimplemented");
    }
}
