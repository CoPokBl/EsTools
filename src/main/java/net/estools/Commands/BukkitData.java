package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.EsPersistentDataType;
import net.estools.ServerApi.Interfaces.*;
import net.estools.Tuple;

import java.util.ArrayList;
import java.util.List;

public class BukkitData extends EsToolsCommand {
    public static final String usage = genUsage("/bukkitdata <get/set/remove> <key> <type> <value>");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        if (args.length < 2) {
            send(sender, usage);
            return false;
        }

        String key = args[1].toLowerCase();
        String task = args[0].toLowerCase();

        EsItemStack item = ((EsPlayer) sender).getMainHand();

        EsItemMeta meta = item.getItemMeta();
        if (meta == null) {
            send(sender, "&cItem does not have nbt tags!");
            return false;
        }

        EsPersistentDataContainer data = meta.getPersistentDataContainer();

        switch (task) {
            case "get": {
                EsPersistentDataType type = data.getType(key);
                Tuple<Integer, String> value = getDataAsString(key, type, data);

                if (value.a() == 2) {
                    send(sender, "&cNBT tag &e\"" + key + "\"&c does not exist!");
                    return false;
                }

                if (value.a() == 1) {
                    send(sender, "&cNBT tag type &e\"" + type + "\"&c is unsupported!");
                    return false;
                }

                send(sender, "&aNBT tag &e\"" + key + "\"&a is &e" + value.b() + " (" + type + ")");
                break;
            }

            case "set": {
                if (args.length < 4) {
                    send(sender, usage);
                    return false;
                }

                String typeString = args[2].toLowerCase();
                String valueString; {
                    StringBuilder valueBuilder = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        valueBuilder.append(args[i]).append(' ');
                    }

                    valueBuilder.deleteCharAt(valueBuilder.length() - 1);
                    valueString = valueBuilder.toString();
                }

                int code = setData(typeString, key, data, valueString);
                if (code == 1) {
                    send(sender, "&cNBT tag type &e\"" + typeString + "\"&c is unsupported!");
                    return false;
                }

                if (code == 2) {
                    String message = "&cInvalid value for type &e\"" + typeString + "\"&c!";
                    if (typeString.endsWith("_array")) {
                        message += " (arrays are separated by spaces)";
                    }

                    send(sender, message);
                    return false;
                }

                item.setItemMeta(meta);

                send(sender, "&aSet NBT tag &e\"" + args[0].toLowerCase() + "\"&a to &e\"" + valueString + "\"&a!");
                break;
            }

            case "remove":
                if (!data.has(key)) {
                    send(sender, "&cNBT tag &e\"" + key + "\"&c does not exist!");
                    return false;
                }

                meta.getPersistentDataContainer().remove(key);
                item.setItemMeta(meta);

                send(sender, "&aRemoved NBT tag &e\"" + key + "\"&a!");
                break;
        }

        return true;
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        switch (args.length) {
            case 1:
                tab.add("get"); tab.add("set"); tab.add("remove");
                break;

            case 2:
                // data.getKeys does not exist, so all we can do is give them a hint on how to format the argument
                if (Main.minecraftVersion.getMinor() < 16) {
                    tab.add("namespace:key");
                    break;
                }

                EsItemStack item = ((EsPlayer) sender).getMainHand();
                EsItemMeta meta = item.getItemMeta();
                if (meta == null) {
                    tab.add("namespace:key");
                    break;
                }

                EsPersistentDataContainer data = meta.getPersistentDataContainer();
                tab.addAll(data.getKeys());

                if (args[0].equalsIgnoreCase("set") && tab.isEmpty()) {
                    tab.add("namespace:key");
                    break;
                }

                break;

            case 3:
                if (!args[0].equalsIgnoreCase("set")) {
                    break;
                }

                tab.add("string"); tab.add("integer");
                tab.add("double"); tab.add("float");
                tab.add("long"); tab.add("short");
                tab.add("byte"); tab.add("byte_array");
                tab.add("int_array"); tab.add("long_array");
                break;
        }

        return tab;
    }

    // A: Success code: 0: Success, 1: Unsupported type, 2: Key does not exist
    // B: Value as a string
    private static Tuple<Integer, String> getDataAsString(String key, EsPersistentDataType type, EsPersistentDataContainer data) {
        if (type == null) {
            return new Tuple<>(2, null);
        }

        switch (type) {
            case String: {
                String value = (String) data.get(key, EsPersistentDataType.String);
                if (value == null) {
                    throw new RuntimeException("Failed to get data, data.getType didnt return null when data.get did");
                }

                return new Tuple<>(0, "\"" + value + "\"");
            }

            case Byte: case Long: case Float:
            case Short: case Double: case Integer:
            case Boolean: {
                Object value = data.get(key, type);
                if (value == null) {
                    throw new RuntimeException("Failed to get data, data.getType didnt return null when data.get did");
                }

                return new Tuple<>(0, value.toString());
            }

            case ByteArray: {
                Byte[] value = (Byte[]) data.get(key, EsPersistentDataType.ByteArray);
                if (value == null) {
                    throw new RuntimeException("Failed to get data, data.getType didnt return null when data.get did");
                }

                return new Tuple<>(0, buildString(value).toString());
            }

            case IntArray: {
                Integer[] value = (Integer[]) data.get(key, EsPersistentDataType.IntArray);
                if (value == null) {
                    throw new RuntimeException("Failed to get data, data.getType didnt return null when data.get did");
                }

                return new Tuple<>(0, buildString(value).toString());
            }

            case LongArray: {
                Long[] value = (Long[]) data.get(key, EsPersistentDataType.LongArray);
                if (value == null) {
                    throw new RuntimeException("Failed to get data, data.getType didnt return null when data.get did");
                }

                return new Tuple<>(0, buildString(value).toString());
            }

            default:
                return new Tuple<>(1, null);
        }
    }

    // 0 = success, 1 = unsupported, 2 = invalid value
    public static int setData(String type, String key, EsPersistentDataContainer data, String value) {
        switch (type) {
            case "string":
                data.set(key, EsPersistentDataType.String, value);
                break;

            case "integer":
                try {
                    data.set(key, EsPersistentDataType.Integer, Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "double":
                try {
                    data.set(key, EsPersistentDataType.Double, Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "float":
                try {
                    data.set(key, EsPersistentDataType.Float, Float.parseFloat(value));
                } catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "long":
                try {
                    data.set(key, EsPersistentDataType.Long, Long.parseLong(value));
                } catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "short":
                try {
                    data.set(key, EsPersistentDataType.Short, Short.parseShort(value));
                } catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "byte":
                try {
                    data.set(key, EsPersistentDataType.Byte, Byte.parseByte(value));
                } catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "byte_array": {
                String[] split = value.split(" ");
                byte[] bytes = new byte[split.length];

                for (int i = 0; i < split.length; i++) {
                    try {
                        bytes[i] = Byte.parseByte(split[i]);
                    } catch (NumberFormatException e) {
                        return 2;
                    }
                }

                data.set(key, EsPersistentDataType.ByteArray, bytes);
                break;
            }

            case "int_array": {
                String[] split = value.split(" ");
                int[] ints = new int[split.length];

                for (int i = 0; i < split.length; i++) {
                    try {
                        ints[i] = Integer.parseInt(split[i]);
                    } catch (NumberFormatException e) {
                        return 2;
                    }
                }

                data.set(key, EsPersistentDataType.IntArray, ints);
                break;
            }

            case "long_array": {
                String[] split = value.split(" ");
                long[] longs = new long[split.length];

                for (int i = 0; i < split.length; i++) {
                    try {
                        longs[i] = Long.parseLong(split[i]);
                    } catch (NumberFormatException e) {
                        return 2;
                    }
                }

                data.set(key, EsPersistentDataType.LongArray, longs);
                break;
            }

            default: {
                return 1;
            }
        }

        return 0;
    }

    private static StringBuilder buildString(Object[] values) {
        StringBuilder sb = new StringBuilder("[");
        for (Object value : values) {
            sb.append(value).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb;
    }
}
