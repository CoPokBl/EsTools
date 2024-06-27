package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemMeta;
import net.estools.Implementation.TestItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

// /lore add text
// /lore set line text
// /lore insert line text
// /lore remove line
public class LoreCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("lore");
    }

    @Test
    public void oneArg() {
        executeAssertOneError("lore add");
        executeAssertOneError("lore set");
        executeAssertOneError("lore insert");
        executeAssertOneError("lore remove");
    }

    @Test
    public void invalidLine() {
        executeAssertOneError("lore set invalid text");
        executeAssertOneError("lore insert invalid text");
        executeAssertOneError("lore remove invalid");
    }

    @Test
    public void noItem() {
        player.setMainHand(null);
        executeAssertOneError("lore add test");
        executeAssertOneError("lore set 1 test2");
        executeAssertOneError("lore insert 1 test3");
        executeAssertOneError("lore remove 1");
    }

    @Test
    public void addLine() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        executeCommand("lore add Hello There");
        Assertions.assertEquals("Hello There", stripColour(item.getItemMeta().getLore().get(0)));
    }

    @Test
    public void setLine() {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();

        List<String> existingLore = new ArrayList<>();
        existingLore.add("bad line");
        meta.setLore(existingLore);

        item.setItemMeta(meta);

        player.setMainHand(item);
        executeCommand("lore set 1 Hello There");
        Assertions.assertEquals("Hello There", stripColour(item.getItemMeta().getLore().get(0)));
    }

    @Test
    public void insertLine() {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();

        List<String> existingLore = new ArrayList<>();
        existingLore.add("bad line");
        meta.setLore(existingLore);

        item.setItemMeta(meta);

        player.setMainHand(item);
        executeCommand("lore insert 1 Hello There");
        Assertions.assertEquals("Hello There", stripColour(item.getItemMeta().getLore().get(0)));
    }

    @Test
    public void removeLine() {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();

        List<String> existingLore = new ArrayList<>();
        existingLore.add("bad line");
        meta.setLore(existingLore);

        item.setItemMeta(meta);

        player.setMainHand(item);
        executeCommand("lore remove 1");
        Assertions.assertEquals(0, item.getItemMeta().getLore().size());
    }
}
