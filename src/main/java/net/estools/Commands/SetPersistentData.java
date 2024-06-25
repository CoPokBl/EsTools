package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.EsPersistentDataType;
import net.estools.ServerApi.Interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class SetPersistentData extends EsToolsCommand {
    public static final String usage = genUsage("/setpersistentdata <key> <type> <value>");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 3) {
            send(sender, usage);
            return true;
        }

        String key = args[0].toLowerCase();
        String typeString = args[1].toLowerCase();
        String valueString;

        {
            StringBuilder valueBuilder = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                valueBuilder.append(args[i]).append(' ');
            }

            valueBuilder.deleteCharAt(valueBuilder.length() - 1);
            valueString = valueBuilder.toString();
        }

        EsItemStack item = ((EsPlayer) sender).getMainHand();
//        NamespacedKey key = GetPersistentData.getNamespacedKey(key);
//
//        if (key == null) {
//            send(sender, "&cInvalid key! examples: 'estools:count', 'backpacks:size', etc");
//            return false;
//        }

        EsItemMeta meta = item == null ? null : item.getItemMeta();
        if (meta == null) {
            send(sender, "&cItem does not have nbt tags!");
            return false;
        }

        EsPersistentDataContainer data = meta.getPersistentDataContainer();

        int code = setNbt(typeString, key, data, valueString);
        if (code == 1) {
            send(sender, "&cNBT tag type &e\"" + typeString + "\"&c is unsupported!");
            return false;
        }

        if (code == 2) {
            send(sender, "&cInvalid value for type &e\"" + typeString + "\"&c! (arrays are separated by spaces)");
            return false;
        }

        item.setItemMeta(meta);

        send(sender, "&aSet NBT tag &e\"" + args[0].toLowerCase() + "\"&a to &e\"" + valueString + "\"&a!");
        return true;
    }

    // 0 = success, 1 = unsupported, 2 = invalid value
    public static int setNbt(String type, String key, EsPersistentDataContainer data, String value) {
        switch (type) {
            case "string":
                data.set(key, EsPersistentDataType.String, value);
                break;

            case "integer":
                try {
                    data.set(key, EsPersistentDataType.Integer, Integer.parseInt(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "double":
                try {
                    data.set(key, EsPersistentDataType.Double, Double.parseDouble(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "float":
                try {
                    data.set(key, EsPersistentDataType.Float, Float.parseFloat(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "long":
                try {
                    data.set(key, EsPersistentDataType.Long, Long.parseLong(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "short":
                try {
                    data.set(key, EsPersistentDataType.Short, Short.parseShort(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "byte":
                try {
                    data.set(key, EsPersistentDataType.Byte, Byte.parseByte(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
                break;

            case "byte_array": {
                String[] split = value.split(" ");
                byte[] bytes = new byte[split.length];

                for (int i = 0; i < split.length; i++) {
                    try {
                        bytes[i] = Byte.parseByte(split[i]);
                    }
                    catch (NumberFormatException e) {
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
                    }
                    catch (NumberFormatException e) {
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
                    }
                    catch (NumberFormatException e) {
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
