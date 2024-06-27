package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemMeta;
import net.estools.Implementation.TestItemStack;
import net.estools.Implementation.TestPersistentDataContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /getpersistentdata <key> <type>
public class GetPersistentDataCommandTest extends EsToolsUnitTest {

    private void setupItem() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
    }

    private void setupItem(String key, Object value) {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();
        TestPersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, null, value);
        item.setItemMeta(meta);
        player.setMainHand(item);
    }

    @Test
    public void noArgsGet() {
        executeAssertOneError("getpersistentdata");
    }

    @Test
    public void justKey() {
        setupItem();
        executeAssertOneError("getpersistentdata somekey");
    }

    @Test
    public void invalidType() {
        setupItem();
        executeAssertOneError("getpersistentdata somekey invalid");
    }

    @Test
    public void getString() {
        setupItem("somekey", "sometext");
        String[] feedback = executeAssertSuccess("getpersistentdata somekey string");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertTrue(feedback[0].contains("sometext"));
    }

    @Test
    public void setInteger() {
        setupItem("someint", 123);
        String[] feedback = executeAssertSuccess("getpersistentdata someint integer");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertTrue(feedback[0].contains("123"));
    }
}
