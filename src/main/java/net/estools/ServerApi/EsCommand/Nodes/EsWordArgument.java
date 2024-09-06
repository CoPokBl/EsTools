package net.estools.ServerApi.EsCommand.Nodes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EsWordArgument extends EsArgumentNode {
    private final Supplier<Set<String>> supplier;

    private EsWordArgument(String id, Supplier<Set<String>> supplier) {
        super(id);
        this.supplier = supplier;
    }

    public static EsWordArgument wordArg(String id, Supplier<Set<String>> supplier) {
        return new EsWordArgument(id, supplier);
    }

    public static EsWordArgument wordArg(String id, Set<String> words) {
        return new EsWordArgument(id, () -> words);
    }

    public static EsWordArgument wordArg(String id, String... words) {
        return new EsWordArgument(id, () -> Collections.unmodifiableSet(new HashSet<>(Arrays.asList(words))));
    }

    public boolean isValidWord(String word) {
        return supplier.get().contains(word);
    }

    public Set<String> getWords() {
        return supplier.get();
    }
}
