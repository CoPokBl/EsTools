package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestItemStack;
import org.junit.jupiter.api.Test;

// /setpersistentdata <key> <type> <value>
public class SetPersistentDataCommandTest extends EsToolsUnitTest {

    @SuppressWarnings("UnusedReturnValue")
    private TestItemStack setupItem() {
        TestItemStack item = genTestItem();
        player.setMainHand(item);
        return item;
    }

    @Test
    public void noArgsSet() {
        executeAssertOneError("setpersistentdata");
    }

    @Test
    public void justKey() {
        setupItem();
        executeAssertOneError("setpersistentdata somekey");
    }

    @Test
    public void justKeyAndType() {
        setupItem();
        executeAssertOneError("setpersistentdata somekey string");
    }

    @Test
    public void invalidType() {
        setupItem();
        executeAssertOneError("setpersistentdata somekey invalid val");
    }

    @Test
    public void invalidValue() {
        setupItem();
        executeAssertOneError("setpersistentdata somekey integer invalid");
    }

//    @Test  TODO: Fix
//    public void setString() {
//        TestItemStack item = setupItem();
//        executeAssertSuccess("setpersistentdata somekey string sometext");
//        TestPersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
//        Assertions.assertEquals(1, data.getValues().size());
//        Assertions.assertEquals("sometext", data.get("somekey", null));
//    }
}
