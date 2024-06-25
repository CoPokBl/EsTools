package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestSign;
import net.estools.Implementation.TestSignSide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /editsign <line> [text]
// /editsign glow/unglow
public class EditSignCommandTest extends EsToolsUnitTest {

    public TestSignSide initSign() {
        TestSign sign = new TestSign(0, 0, 0);
        TestSignSide side = new TestSignSide();
        player.setTargetBlock(sign);
        player.setTargetSignSide(side);
        return side;
    }

    @Test
    public void noArgs() {
        executeAssertOneError("editsign");
    }

    @Test
    public void noSign() {
        player.setTargetBlock(null);
        executeAssertOneError("editsign 1 test");
    }

    @Test
    public void invalidLine() {
        initSign();
        executeAssertOneError("editsign invalid");
    }

    @Test
    public void setLine() {
        TestSignSide side = initSign();
        executeCommand("editsign 1 test");
        Assertions.assertEquals("test", side.getLines()[0]);
    }

    @Test
    public void setGlow() {
        TestSignSide side = initSign();
        executeCommand("editsign glow");
        Assertions.assertTrue(side.isGlowingText());
    }

    @Test
    public void setUnGlow() {
        TestSignSide side = initSign();
        side.setGlowingText(true);
        executeCommand("editsign unglow");
        Assertions.assertFalse(side.isGlowingText());
    }

    @Test
    public void clearText() {
        TestSignSide side = initSign();
        side.setLine(0, "test");
        executeCommand("editsign 1");
        Assertions.assertEquals("", side.getLines()[0]);
    }
}
