package by.latushko.xmlapp.builder;

import by.latushko.xmlapp.builder.handler.DeviceErrorHandler;
import by.latushko.xmlapp.builder.handler.DeviceHandler;
import by.latushko.xmlapp.exception.DeviceDataException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;

public class SaxDeviceBuilder extends AbstractDeviceBuilder{
    private DeviceHandler handler;
    private XMLReader reader;

    public SaxDeviceBuilder() throws DeviceDataException {
        handler = new DeviceHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new DeviceErrorHandler());
        } catch (SAXException | ParserConfigurationException e) {
            throw new DeviceDataException("Configuration of SAX parser failed", e);
        }
    }

    public void buildDevices(String xmlPath) throws DeviceDataException {
        try {
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(xmlPath);

            reader.parse(resource.getFile());
        } catch (IOException | SAXException e) {
            throw new DeviceDataException("Building data by SAX parser failed", e);
        }
        devices = handler.getDevices();
    }
}
