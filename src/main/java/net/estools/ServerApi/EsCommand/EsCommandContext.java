package net.estools.ServerApi.EsCommand;

import net.estools.ServerApi.Interfaces.EsCommandSender;

import java.util.HashMap;
import java.util.Map;

public class EsCommandContext {
    private final EsCommandSender sender;
    private final Map<String, Object> args;

    public EsCommandContext(EsCommandSender sender) {
        this.sender = sender;
        args = new HashMap<>();
    }

    public EsCommandSender sender() {
        return sender;
    }

    public void addArgument(String id, Object value) {
        args.put(id, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgument(String id) {
        return (T) args.get(id);
    }
}
