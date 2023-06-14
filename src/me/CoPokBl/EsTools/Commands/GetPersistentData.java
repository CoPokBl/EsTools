package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
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
import java.util.Objects;

public class GetPersistentData extends CMD {
    public static final String usage = genUsage("/getpersistentdata <key> <type>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 2) {
            s(sender, usage);
            return false;
        }

        String tagString = args[0].toLowerCase();
        String typeString = args[1].toLowerCase();

        ItemStack item = getMainHand((Player) sender);
        NamespacedKey key = NamespacedKey.fromString(tagString, Main.current);
        if (key == null) {
            s(sender, "&cInvalid key! examples: 'estools:count', 'backpacks:size', etc");
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            s(sender, "&cItem does not have nbt tags!");
            return false;
        }

        PersistentDataContainer data = meta.getPersistentDataContainer();

        String value = getData(typeString, key, data);
        if (value.equals("null")) {
            s(sender, "&cNBT tag &e\"" + tagString + "\"&c does not exist!");
            return false;
        }

        if (value.equals("unsupported")) {
            s(sender, "&cNBT tag type &e\"" + typeString + "\"&c is unsupported!");
            return false;
        }

        if (value.equals("wrong")) {
            s(sender, "&cNBT tag &e\"" + tagString + "\"&c exists, but is not a " + typeString + "!");
            return false;
        }

        s(sender, "&aNBT tag &e\"" + tagString + "\"&a is &e\"" + value + "\"&a!");
        return true;
    }

    private static String getData(String type, NamespacedKey key, PersistentDataContainer data) {
        try {
            switch (type) {
                case "string" -> {
                    String value = data.get(key, PersistentDataType.STRING);
                    return Objects.requireNonNullElse(value, "null");

                }

                case "integer" -> {
                    Integer value = data.get(key, PersistentDataType.INTEGER);
                    if (value == null) {
                        return "null";
                    }
                    return value.toString();
                }

                case "double" -> {
                    Double value = data.get(key, PersistentDataType.DOUBLE);
                    if (value == null) {
                        return "null";
                    }
                    return value.toString();
                }

                case "float" -> {
                    Float value = data.get(key, PersistentDataType.FLOAT);
                    if (value == null) {
                        return "null";
                    }
                    return value.toString();
                }

                case "long" -> {
                    Long value = data.get(key, PersistentDataType.LONG);
                    if (value == null) {
                        return "null";
                    }
                    return value.toString();
                }

                case "short" -> {
                    Short value = data.get(key, PersistentDataType.SHORT);
                    if (value == null) {
                        return "null";
                    }
                    return value.toString();
                }

                case "byte" -> {
                    Byte value = data.get(key, PersistentDataType.BYTE);
                    if (value == null) {
                        return "null";
                    }
                    return value.toString();
                }

                case "byte_array" -> {
                    byte[] value = data.get(key, PersistentDataType.BYTE_ARRAY);
                    if (value == null) {
                        return "null";
                    }

                    return buildString(ArrayUtils.toObject(value)).toString();
                }

                case "int_array" -> {
                    int[] value = data.get(key, PersistentDataType.INTEGER_ARRAY);
                    if (value == null) {
                        return "null";
                    }

                    return buildString(ArrayUtils.toObject(value)).toString();
                }

                case "long_array" -> {
                    long[] value = data.get(key, PersistentDataType.LONG_ARRAY);
                    if (value == null) {
                        return "null";
                    }

                    return buildString(ArrayUtils.toObject(value)).toString();
                }
            }
        } catch (IllegalArgumentException e) {
            return "wrong";
        }

        return "unsupported";
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
        List<String> tab = new ArrayList<String>();

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
