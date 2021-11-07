package by.latushko.xmlapp.builder;

import by.latushko.xmlapp.entity.Device;
import by.latushko.xmlapp.exception.DeviceDataException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDeviceBuilder {
    protected Set<Device> devices;

    public AbstractDeviceBuilder() {
        this.devices = new HashSet<>();
    }

    public AbstractDeviceBuilder(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public abstract void buildDevices(String filename) throws DeviceDataException;
}
