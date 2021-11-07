package by.latushko.xmlapp.factory;

import by.latushko.xmlapp.builder.AbstractDeviceBuilder;
import by.latushko.xmlapp.builder.DomDeviceBuilder;
import by.latushko.xmlapp.builder.SaxDeviceBuilder;
import by.latushko.xmlapp.builder.StaxDeviceBuilder;
import by.latushko.xmlapp.exception.DeviceDataException;

public class BuilderFactory {
    private enum TypeParser{
        SAX, STAX, DOM
    }

    private BuilderFactory(){
    }

    public static AbstractDeviceBuilder createDeviceBuilder(String typeParser) throws DeviceDataException {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type){
            case DOM -> {
                return new DomDeviceBuilder();
            }
            case SAX -> {
                return new SaxDeviceBuilder();
            }
            case STAX -> {
                return new StaxDeviceBuilder();
            }
            default -> throw new EnumConstantNotPresentException(
                    type.getDeclaringClass(), type.name());
        }
    }
}
