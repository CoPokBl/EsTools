package net.estools.ServerApi.EsCommand.Nodes;

import net.estools.ServerApi.EsCommand.EsCommandContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EsWordArgument extends EsArgumentNode {
    private final WordSupplier supplier;

    private EsWordArgument(String id, WordSupplier supplier) {
        super(id);
        this.supplier = supplier;
    }

    public static EsWordArgument wordArg(String id, WordSupplier supplier) {
        return new EsWordArgument(id, supplier);
    }

    public static EsWordArgument wordArg(String id, Set<String> words) {
        return new EsWordArgument(id, (ignored) -> words);
    }

    public static EsWordArgument wordArg(String id, String... words) {
        return new EsWordArgument(id, (ignored) -> Collections.unmodifiableSet(new HashSet<>(Arrays.asList(words))));
    }

    public boolean isValidWord(String word, EsCommandContext context) {
        return supplier.get(context).contains(word);
    }

    public Set<String> getWords(EsCommandContext context) {
        return supplier.get(context);
    }

    @FunctionalInterface
    public interface WordSupplier {
        Set<String> get(EsCommandContext context);
    }
}
