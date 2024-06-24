package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.Implementation.TestItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RenameCommandTest extends EsToolsUnitTest {

    @Test
    public void noItem() {
        player.setMainHand(null);
        executeAssertError("rename");
    }

    @Test
    public void renameItem() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeAssertSuccess("rename Hello There");
        Assertions.assertEquals("Hello There", stripReset(item.getItemMeta().getDisplayName()));
    }

    @Test
    public void removeName() {
        TestItemStack item = genTestItem();
        item.getItemMeta().setDisplayName("Hello There");
        player.setMainHand(item);
        executeAssertSuccess("rename");
        Assertions.assertEquals("", stripReset(item.getItemMeta().getDisplayName()));
    }
}
