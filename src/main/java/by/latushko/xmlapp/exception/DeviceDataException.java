package by.latushko.xmlapp.exception;

public class DeviceDataException extends Exception{
    public DeviceDataException() {
        super();
    }

    public DeviceDataException(String message) {
        super(message);
    }

    public DeviceDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceDataException(Throwable cause) {
        super(cause);
    }
}
