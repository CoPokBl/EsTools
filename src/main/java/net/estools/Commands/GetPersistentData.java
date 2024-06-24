package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.EsPersistentDataType;
import net.estools.ServerApi.Interfaces.*;
import net.estools.Tuple;

import java.util.ArrayList;
import java.util.List;

public class GetPersistentData extends EsToolsCommand {
    public static final String usage = genUsage("/getpersistentdata <key> <type>");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 2) {
            send(sender, usage);
            return false;
        }

        String key = args[0].toLowerCase();
        String typeString = args[1].toLowerCase();

        EsItemStack item = ((EsPlayer) sender).getMainHand();
//        NamespacedKey key = getNamespacedKey(key);
//        if (key == null) {
//            send(sender, "&cInvalid key! examples: 'estools:count', 'backpacks:size', etc");
//            return false;
//        }

        EsItemMeta meta = item.getItemMeta();
        if (meta == null) {
            send(sender, "&cItem does not have nbt tags!");
            return false;
        }

        EsPersistentDataContainer data = meta.getPersistentDataContainer();

        Tuple<Integer, String> value = getData(typeString, key, data);
        if (value.a() == 3) {
            send(sender, "&cNBT tag &e\"" + key + "\"&c does not exist!");
            return false;
        }

        if (value.a() == 1) {
            send(sender, "&cNBT tag type &e\"" + typeString + "\"&c is unsupported!");
            return false;
        }

        if (value.a() == 2) {
            send(sender, "&cNBT tag &e\"" + key + "\"&c exists, but is not a " + typeString + "!");
            return false;
        }

        send(sender, "&aNBT tag &e\"" + key + "\"&a is &e" + value.b() + "&a!");
        return true;
    }

    private static Tuple<Integer, String> getData(String type, String key, EsPersistentDataContainer data) {
        try {
            switch (type) {
                case "string": {
                    String value = (String) data.get(key, EsPersistentDataType.String);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, "\"" + value + "\"");
                }

                case "integer": {
                    Integer value = (Integer) data.get(key, EsPersistentDataType.Integer);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "double": {
                    Double value = (Double) data.get(key, EsPersistentDataType.Double);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "float": {
                    Float value = (Float) data.get(key, EsPersistentDataType.Float);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "long": {
                    Long value = (Long) data.get(key, EsPersistentDataType.Long);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "short": {
                    Short value = (Short) data.get(key, EsPersistentDataType.Short);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "byte": {
                    Byte value = (Byte) data.get(key, EsPersistentDataType.Byte);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }
                    return new Tuple<>(0, value.toString());
                }

                case "byte_array": {
                    Byte[] value = (Byte[]) data.get(key, EsPersistentDataType.ByteArray);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, buildString(value).toString());
                }

                case "int_array": {
                    Integer[] value = (Integer[]) data.get(key, EsPersistentDataType.IntArray);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, buildString(value).toString());
                }

                case "long_array": {
                    Long[] value = (Long[]) data.get(key, EsPersistentDataType.LongArray);
                    if (value == null) {
                        return new Tuple<>(3, null);
                    }

                    return new Tuple<>(0, buildString(value).toString());
                }
            }
        } catch (IllegalArgumentException e) {
            return new Tuple<>(2, null);
        }

        return new Tuple<>(1, null);
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
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
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
