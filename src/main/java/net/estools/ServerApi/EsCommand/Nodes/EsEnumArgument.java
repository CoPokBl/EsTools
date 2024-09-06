package net.estools.ServerApi.EsCommand.Nodes;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class EsEnumArgument<T extends Enum<T>> extends EsArgumentNode {
    private final Class<? extends Enum<T>> clazz;
    private final Set<String> names;

    private EsEnumArgument(String id, Class<? extends Enum<T>> clazz) {
        super(id);
        this.clazz = clazz;

        names = Collections.unmodifiableSet(Arrays.stream(clazz.getEnumConstants()).map(Enum::name).collect(Collectors.toSet()));
    }

    public static <T extends Enum<T>> EsEnumArgument<T> enumArg(String id, Class<? extends Enum<T>> clazz) {
        return new EsEnumArgument<>(id, clazz);
    }

    public boolean isValidName(String name) {
        return names.contains(name);
    }

    public Set<String> getNames() {
        return names;
    }

    public @Nullable Enum<T> getValue(String name) {
        return Arrays.stream(clazz.getEnumConstants()).filter(a -> a.name().equals(name)).findAny().orElse(null);
    }
}
