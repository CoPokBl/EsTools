package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.Tuple;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GetPersistentData extends EsToolsCommand {
    public static final String usage = genUsage("/getpersistentdata <key> <type>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 2) {
            send(sender, usage);
            return false;
        }

        String tagString = args[0].toLowerCase();
        String typeString = args[1].toLowerCase();

        ItemStack item = getMainHand((Player) sender);
        NamespacedKey key = getNamespacedKey(tagString);
        if (key == null) {
            send(sender, "&cInvalid key! examples: 'estools:count', 'backpacks:size', etc");
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            send(sender, "&cItem does not have nbt tags!");
            return false;
        }

        PersistentDataContainer data = meta.getPersistentDataContainer();

        Tuple<Integer, String> value = getData(typeString, key, data);
        if (value.a() == 3) {
            send(sender, "&cNBT tag &e\"" + tagString + "\"&c does not exist!");
            return false;
        }

        if (value.a() == 1) {
            send(sender, "&cNBT tag type &e\"" + typeString + "\"&c is unsupported!");
            return false;
        }

        if (value.a() == 2) {
            send(sender, "&cNBT tag &e\"" + tagString + "\"&c exists, but is not a " + typeString + "!");
            return false;
        }

        send(sender, "&aNBT tag &e\"" + tagString + "\"&a is &e" + value.b() + "&a!");
        return true;
    }

    private static Tuple<Integer, String> getData(String type, NamespacedKey key, PersistentDataContainer data) {
        try {
            switch (type) {
                case "string": {
                    String value = data.get(key, PersistentDataType.STRING);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, "\"" + value + "\"");
                }

                case "integer": {
                    Integer value = data.get(key, PersistentDataType.INTEGER);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "double": {
                    Double value = data.get(key, PersistentDataType.DOUBLE);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "float": {
                    Float value = data.get(key, PersistentDataType.FLOAT);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "long": {
                    Long value = data.get(key, PersistentDataType.LONG);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "short": {
                    Short value = data.get(key, PersistentDataType.SHORT);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "byte": {
                    Byte value = data.get(key, PersistentDataType.BYTE);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "byte_array": {
                    byte[] value = data.get(key, PersistentDataType.BYTE_ARRAY);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, buildString(ArrayUtils.toObject(value)).toString());
                }

                case "int_array": {
                    int[] value = data.get(key, PersistentDataType.INTEGER_ARRAY);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, buildString(ArrayUtils.toObject(value)).toString());
                }

                case "long_array": {
                    long[] value = data.get(key, PersistentDataType.LONG_ARRAY);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, buildString(ArrayUtils.toObject(value)).toString());
                }
            }
        } catch (IllegalArgumentException e) {
            return new Tuple<>(2, null);
        }

        return new Tuple<>(1, null);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static NamespacedKey getNamespacedKey(String keyString) {
        if (Main.majorVersion >= 16) {
            return NamespacedKey.fromString(keyString, Main.plugin);
        }

        String[] parts = keyString.split(":");
        if (parts.length == 2) {
            return new NamespacedKey(parts[0], parts[1]);
        } else if (parts.length == 1) {  // Incorrectly formatted key
            String pluginName = Main.getPlugin(Main.class).getName();
            return new NamespacedKey(pluginName, parts[0]);
        }

        return null;
    }

    private static <T> StringBuilder buildString(T[] values) {
        StringBuilder sb = new StringBuilder("[");
        for (T value : values) {
            sb.append(value).append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        if (args.length == 2) {
            tab.add("string");
            tab.add("integer");
            tab.add("double");
            tab.add("float");
            tab.add("long");
            tab.add("short");
            tab.add("byte");
            tab.add("byte_array");
            tab.add("int_array");
            tab.add("long_array");
        }

        return tab;
    }
}
