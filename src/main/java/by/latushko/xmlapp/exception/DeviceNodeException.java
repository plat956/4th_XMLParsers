package by.latushko.xmlapp.exception;

public class DeviceNodeException extends Exception{
    public DeviceNodeException() {
        super();
    }

    public DeviceNodeException(String message) {
        super(message);
    }

    public DeviceNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceNodeException(Throwable cause) {
        super(cause);
    }
}
