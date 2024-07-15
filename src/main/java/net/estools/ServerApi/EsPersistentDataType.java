package net.estools.ServerApi;

public enum EsPersistentDataType {
    String,
    Boolean,
    Integer,
    Byte,
    Float,
    Double,
    Short,
    Long,
    ByteArray,
    IntArray,
    LongArray,
    TagContainer;

    @Override
    public java.lang.String toString() {
        switch (this) {
            case Boolean:
                return "Boolean";
            case Integer:
                return "Integer";
            case Byte:
                return "Byte";
            case Float:
                return "Float";
            case Double:
                return "Double";
            case Short:
                return "Short";
            case Long:
                return "Long";
            case ByteArray:
                return "Byte Array";
            case IntArray:
                return "Int Array";
            case LongArray:
                return "Long Array";
            case TagContainer:
                return "Tag Container";
            case String:
                return "String";
        }

        return "None";
    }
}
