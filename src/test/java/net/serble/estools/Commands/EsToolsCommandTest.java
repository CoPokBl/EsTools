package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class EsToolsCommandTest extends EsToolsUnitTest {

    @Test
    public void displaysVersion() {
        String[] result = executeCommand("estools");
        Assert.assertTrue("/estools didn't print", result.length != 0);
        Assert.assertTrue(stripColour(result[result.length-1]).startsWith("EsTools v"));
    }
}
