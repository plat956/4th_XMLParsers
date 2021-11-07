package by.latushko.xmlapp.builder;

import by.latushko.xmlapp.builder.tag.DeviceXmlTag;
import by.latushko.xmlapp.entity.Device;
import by.latushko.xmlapp.entity.EmbeddedDevice;
import by.latushko.xmlapp.entity.PortableDevice;
import by.latushko.xmlapp.entity.type.DeviceGroup;
import by.latushko.xmlapp.entity.type.DeviceOrigin;
import by.latushko.xmlapp.entity.type.EmbeddedDeviceType;
import by.latushko.xmlapp.exception.DeviceDataException;
import by.latushko.xmlapp.exception.DeviceNodeException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.YearMonth;

import static by.latushko.xmlapp.entity.Device.DEFAULT_VENDOR_SITE;

public class StaxDeviceBuilder extends AbstractDeviceBuilder{
    private static final Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    public StaxDeviceBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildDevices(String xmlPath) throws DeviceDataException {
        XMLStreamReader reader;
        String name;

        ClassLoader loader = getClass().getClassLoader();
        URL resource = loader.getResource(xmlPath);

        try(FileInputStream inputStream = new FileInputStream(new File(resource.getFile()))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(DeviceXmlTag.PORTABLE_DEVICE.getValue()) ||
                            name.equals(DeviceXmlTag.EMBEDDED_DEVICE.getValue())) {
                        Device device = buildDevice(reader, DeviceXmlTag.valueOf(formatName(name)));
                        logger.log(Level.INFO, "Device id: {} has been built by STAX builder", device.getDeviceId());
                        devices.add(device);
                    }
                }
            }
        } catch (XMLStreamException | IOException | DeviceNodeException e) {
            throw new DeviceDataException("Building data by SAX parser failed", e);
        }
    }

    private Device buildDevice(XMLStreamReader reader, DeviceXmlTag rootNode) throws XMLStreamException, DeviceNodeException {
        Device currentDevice;
        if (rootNode.equals(DeviceXmlTag.PORTABLE_DEVICE)) {
            currentDevice = new PortableDevice();
        } else if(rootNode.equals(DeviceXmlTag.EMBEDDED_DEVICE)) {
            currentDevice = new EmbeddedDevice();
        } else {
            throw new DeviceNodeException("Unknown device node: " + rootNode.getValue());
        }

        currentDevice.setDeviceId(reader.getAttributeValue(null, "id"));

        String vendorSite = reader.getAttributeValue(null, "vendor-site");
        if(vendorSite == null) vendorSite = DEFAULT_VENDOR_SITE;

        currentDevice.setVendorSite(vendorSite);

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (DeviceXmlTag.valueOf(formatName(name))) {
                        case NAME -> currentDevice.setName(getXMLText(reader));
                        case ORIGIN -> currentDevice.setOrigin(DeviceOrigin.fromString(getXMLText(reader)));
                        case PURCHASE_DATE -> currentDevice.setPurchaseDate(YearMonth.parse(getXMLText(reader)));
                        case GROUP -> currentDevice.setGroup(DeviceGroup.fromString(getXMLText(reader)));
                        case PRICE -> currentDevice.setPrice(new BigDecimal(getXMLText(reader)));
                        case CRITICAL -> currentDevice.setCritical(Boolean.valueOf(getXMLText(reader)));
                        case RECHARGEABLE -> {
                            PortableDevice device = (PortableDevice) currentDevice;
                            device.setRechargeable(Boolean.valueOf(getXMLText(reader)));
                        }
                        case PROTECTIVE_CASE -> {
                            PortableDevice device = (PortableDevice) currentDevice;
                            device.setProtectiveCase(Boolean.valueOf(getXMLText(reader)));
                        }
                        case TYPE -> {
                            EmbeddedDevice device = (EmbeddedDevice) currentDevice;
                            device.setType(EmbeddedDeviceType.fromString(getXMLText(reader)));
                        }
                        default -> throw new EnumConstantNotPresentException(DeviceXmlTag.class, name);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (DeviceXmlTag.valueOf(formatName(name)) == rootNode) {
                        return currentDevice;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <" + rootNode.getValue() + ">");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private String formatName(String value) {
        return value.replace("-", "_").toUpperCase();
    }
}
