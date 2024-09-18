package net.estools.ServerApi.EsCommand.Nodes;

import net.estools.ServerApi.EsCommand.EsCommandContext;

public abstract class EsNumberArgument extends EsArgumentNode {
    private final MinMaxSupplier supplier;

    protected EsNumberArgument(String id, MinMaxSupplier supplier) {
        super(id);
        this.supplier = supplier;
    }

    protected EsNumberArgument(String id, Number min, Number max) {
        super(id);
        this.supplier = (ignored) -> new MinMax(min, max);
    }

    public Number getMin(EsCommandContext context) {
        return supplier.get(context).min();
    }

    public Number getMax(EsCommandContext context) {
        return supplier.get(context).max();
    }

    public MinMax getMinMax(EsCommandContext context) {
        return supplier.get(context);
    }

    @FunctionalInterface
    public interface MinMaxSupplier {
        MinMax get(EsCommandContext context);
    }

    public static class MinMax {
        private final Number min;
        private final Number max;

        public MinMax(Number min, Number max) {
            this.min = min;
            this.max = max;
        }

        public Number min() {
            return min;
        }

        public Number max() {
            return max;
        }
    }
}
