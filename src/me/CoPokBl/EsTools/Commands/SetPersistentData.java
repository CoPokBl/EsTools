package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SetPersistentData extends CMD {
    public static final String usage = genUsage("/setpersistentdata <key> <value>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 3) {
            s(sender, usage);
            return true;
        }

        String keyString = args[0].toLowerCase();
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

        ItemStack item = getMainHand((Player) sender);
        NamespacedKey key = NamespacedKey.fromString(keyString, Main.current);
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

        int code = setNbt(typeString, key, data, valueString);
        if (code == 1) {
            s(sender, "&cNBT tag type &e\"" + typeString + "\"&c is unsupported!");
            return false;
        }

        if (code == 2) {
            s(sender, "&cInvalid value for type &e\"" + typeString + "\"&c! (arrays are separated by spaces)");
            return false;
        }

        item.setItemMeta(meta);

        s(sender, "&aSet NBT tag &e\"" + args[0].toLowerCase() + "\"&a to &e\"" + valueString + "\"&a!");
        return true;
    }

    // 0 = success, 1 = unsupported, 2 = invalid value
    public static int setNbt(String type, NamespacedKey key, PersistentDataContainer data, String value) {
        switch (type) {
            case "string" -> {
                data.set(key, PersistentDataType.STRING, value);
            }

            case "integer" -> {
                try {
                    data.set(key, PersistentDataType.INTEGER, Integer.parseInt(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
            }

            case "double" -> {
                try {
                    data.set(key, PersistentDataType.DOUBLE, Double.parseDouble(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
            }

            case "float" -> {
                try {
                    data.set(key, PersistentDataType.FLOAT, Float.parseFloat(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
            }

            case "long" -> {
                try {
                    data.set(key, PersistentDataType.LONG, Long.parseLong(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
            }

            case "short" -> {
                try {
                    data.set(key, PersistentDataType.SHORT, Short.parseShort(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
            }

            case "byte" -> {
                try {
                    data.set(key, PersistentDataType.BYTE, Byte.parseByte(value));
                }
                catch (NumberFormatException e) {
                    return 2;
                }
            }

            case "byte_array" -> {
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

                data.set(key, PersistentDataType.BYTE_ARRAY, bytes);
            }

            case "int_array" -> {
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

                data.set(key, PersistentDataType.INTEGER_ARRAY, ints);
            }

            case "long_array" -> {
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

                data.set(key, PersistentDataType.LONG_ARRAY, longs);
            }

            default -> {
                return 1;
            }
        }

        return 0;
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
