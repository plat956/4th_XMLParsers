package by.latushko.xmlapp;

import by.latushko.xmlapp.builder.DomDeviceBuilder;
import by.latushko.xmlapp.builder.SaxDeviceBuilder;
import by.latushko.xmlapp.builder.StaxDeviceBuilder;
import by.latushko.xmlapp.exception.DeviceDataException;
import by.latushko.xmlapp.validator.DeviceXmlValidator;

import java.io.File;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws DeviceDataException {
        ClassLoader loader = DeviceXmlValidator.class.getClassLoader();

        URL resourceSchema = loader.getResource("data/devices.xml");
        String schemaPath = new File(resourceSchema.getFile()).getPath();

        SaxDeviceBuilder builder = new SaxDeviceBuilder();
        builder.buildDevices(schemaPath);
        builder = null;

        DomDeviceBuilder domDeviceBuilder = new DomDeviceBuilder();
        domDeviceBuilder.buildDevices(schemaPath);
        domDeviceBuilder = null;

        StaxDeviceBuilder staxDeviceBuilder = new StaxDeviceBuilder();
        staxDeviceBuilder.buildDevices(schemaPath);
        staxDeviceBuilder = null;

        //devices = null;
    }
}
