package net.estools;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Utils {
    public static String keyToDisplayName(String key) {
        StringBuilder name = new StringBuilder(key);
        name.setCharAt(0, Character.toUpperCase(name.charAt(0)));

        int index = name.indexOf("_");
        while (index != -1) {
            name.setCharAt(index, ' ');
            if (index < name.length()) { // Edge case where item id ends with '_'
                name.setCharAt(index+1, Character.toUpperCase(name.charAt(index+1)));
            }

            index = name.indexOf("_");
        }

        return name.toString();
    }

    public static String getStacktrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "resource"})
    public static boolean deleteFolder(File folder) {
        try {
            Files.walk(Paths.get(folder.getPath()))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
