package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemMeta;
import net.estools.Implementation.TestItemStack;
import net.estools.Implementation.TestPersistentDataContainer;
import net.estools.ServerApi.EsPersistentDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BukkitDataTest extends EsToolsUnitTest {
    @SuppressWarnings("UnusedReturnValue")
    private TestItemStack setupItem() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        return item;
    }

    private TestItemStack setupItem(String key, Object value, EsPersistentDataType type) {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();
        TestPersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, type, value);
        item.setItemMeta(meta);
        player.setMainHand(item);
        return item;
    }

    @Test
    public void noArgsSet() {
        executeAssertOneError("bukkitdata");
    }

    // SET

    @Test
    public void justSet() {
        setupItem();
        executeAssertOneError("bukkitdata set");
    }

    @Test
    public void justSetKey() {
        setupItem();
        executeAssertOneError("bukkitdata set somekey");
    }

    @Test
    public void justSetKeyAndType() {
        setupItem();
        executeAssertOneError("bukkitdata set somekey string");
    }

    @Test
    public void invalidSetType() {
        setupItem();
        executeAssertOneError("bukkitdata set somekey invalid val");
    }

    @Test
    public void invalidValue() {
        setupItem();
        executeAssertOneError("bukkitdata set somekey integer invalid");
    }

    @Test
    public void setString() {
        TestItemStack item = setupItem();
        executeAssertSuccess("bukkitdata set somekey string sometext");
        TestPersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        Assertions.assertEquals(1, data.getValues().size());
        Assertions.assertEquals("sometext", data.get("somekey", EsPersistentDataType.String));
    }

    @Test
    public void setInteger() {
        TestItemStack item = setupItem();
        executeAssertSuccess("bukkitdata set somekey integer 123");
        TestPersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        Assertions.assertEquals(1, data.getValues().size());
        Assertions.assertEquals(123, data.get("somekey", EsPersistentDataType.Integer));
    }

    @Test
    public void setIntArray() {
        TestItemStack item = setupItem();
        executeAssertSuccess("bukkitdata set somekey int_array 123 456 789");
        TestPersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        Assertions.assertEquals(1, data.getValues().size());

        int[] value = (int[])data.get("somekey", EsPersistentDataType.IntArray);
        Assertions.assertEquals(123, value[0]);
        Assertions.assertEquals(456, value[1]);
        Assertions.assertEquals(789, value[2]);
    }

    // GET

    @Test
    public void justGet() {
        setupItem();
        executeAssertOneError("bukkitdata get");
    }

    @Test
    public void justGetKey() {
        setupItem();
        executeAssertOneError("bukkitdata get somekey");
    }

    @Test
    public void getInvalidType() {
        setupItem();
        executeAssertOneError("bukkitdata get somekey invalid");
    }

    @Test
    public void getString() {
        setupItem("somekey", "sometext", EsPersistentDataType.String);
        String[] feedback = executeAssertSuccess("bukkitdata get somekey string");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertTrue(feedback[0].contains("sometext"));
    }

    @Test
    public void getInteger() {
        setupItem("someint", 123, EsPersistentDataType.Integer);
        String[] feedback = executeAssertSuccess("bukkitdata get someint integer");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertTrue(feedback[0].contains("123"));
    }

    @Test
    public void getIntArray() {
        setupItem("someint", new Integer[] {123, 456, 789}, EsPersistentDataType.IntArray);
        String[] feedback = executeAssertSuccess("bukkitdata get someint int_array");
        Assertions.assertEquals(1, feedback.length);
        Assertions.assertTrue(feedback[0].contains("[123, 456, 789]"));
    }

    @Test
    public void getNonExistent() {
        setupItem();
        executeAssertOneError("bukkitdata get somekey string");
    }

    // REMOVE

    @Test
    public void justRemove() {
        executeAssertOneError("bukkitdata remove");
    }

    @Test
    public void remove() {
        TestItemStack item = setupItem("somekey", "sometext", EsPersistentDataType.String);
        executeAssertSuccess("bukkitdata remove somekey");
        Assertions.assertNull(item.getItemMeta().getPersistentDataContainer().getValues().get("somekey"));
    }

    @Test
    public void removeNonExistent() {
        setupItem();
        executeAssertOneError("bukkitdata remove somekey");
    }
}
