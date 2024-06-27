package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemMeta;
import net.estools.Implementation.TestItemStack;
import net.estools.Implementation.TestPersistentDataContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /removepersistentdata <key> <type>
public class RemovePersistentDataCommandTest extends EsToolsUnitTest {

    @SuppressWarnings("SameParameterValue")  // Neater
    private TestItemStack setupItem(String key, Object value) {
        TestItemStack item = genTestItem();
        TestItemMeta meta = item.getItemMeta();
        TestPersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, null, value);
        item.setItemMeta(meta);
        player.setMainHand(item);
        return item;
    }

    @Test
    public void noArgsGet() {
        executeAssertOneError("removepersistentdata");
    }

    @Test
    public void remove() {
        TestItemStack item = setupItem("somekey", "sometext");
        executeAssertSuccess("removepersistentdata somekey");
        Assertions.assertNull(item.getItemMeta().getPersistentDataContainer().getValues().get("somekey"));
    }
}
