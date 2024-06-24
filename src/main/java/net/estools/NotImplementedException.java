package net.estools;

@SuppressWarnings("unused")  // This will only be used while things are being implemented
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {

    }

    public NotImplementedException(String msg) {
        super(msg);
    }
}
