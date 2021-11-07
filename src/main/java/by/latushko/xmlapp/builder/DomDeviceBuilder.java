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
import by.latushko.xmlapp.validator.DeviceXmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.YearMonth;
import static by.latushko.xmlapp.entity.Device.DEFAULT_VENDOR_SITE;

public class DomDeviceBuilder extends AbstractDeviceBuilder{
    private static final Logger logger = LogManager.getLogger();
    private DocumentBuilder docBuilder;

    public DomDeviceBuilder() throws DeviceDataException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new DeviceDataException("Configuration of DOM parser failed", e);
        }
    }

    @Override
    public void buildDevices(String xmlPath) throws DeviceDataException {
        Document doc;
        try {
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(xmlPath);

            doc = docBuilder.parse(resource.getFile());
            Element root = doc.getDocumentElement();

            iterateDevicesByNodeName(root, DeviceXmlTag.EMBEDDED_DEVICE);
            iterateDevicesByNodeName(root, DeviceXmlTag.PORTABLE_DEVICE);
        } catch (IOException | SAXException | DeviceNodeException e) {
            throw new DeviceDataException("Building data by DOM parser failed", e);
        }
    }

    private void iterateDevicesByNodeName(Element root, DeviceXmlTag node) throws DeviceNodeException {
        NodeList devicesList = root.getElementsByTagName(node.getValue());
        for (int i = 0; i < devicesList.getLength(); i++) {
            Element deviceElement = (Element) devicesList.item(i);
            Device device = buildDevice(deviceElement, node);
            logger.log(Level.INFO, "Device id: {} has been built by DOM builder", device.getDeviceId());
            devices.add(device);
        }
    }

    private Device buildDevice(Element deviceElement, DeviceXmlTag type) throws DeviceNodeException {
        String id = deviceElement.getAttribute("id");
        String vendorSite = deviceElement.hasAttribute("vendor-site") ?
                deviceElement.getAttribute("vendor-site") :
                DEFAULT_VENDOR_SITE;
        String name = getElementTextContent(deviceElement, "name");
        DeviceOrigin origin = DeviceOrigin.fromString(getElementTextContent(deviceElement, "origin"));
        YearMonth purchaseDate = YearMonth.parse(getElementTextContent(deviceElement, "purchase-date"));
        DeviceGroup group = DeviceGroup.fromString(getElementTextContent(deviceElement, "group"));
        BigDecimal price = new BigDecimal(getElementTextContent(deviceElement, "price"));
        Boolean critical = Boolean.valueOf(getElementTextContent(deviceElement, "critical"));

        Device device;

        switch (type) {
            case EMBEDDED_DEVICE -> {
                device = new EmbeddedDevice();
                EmbeddedDeviceType deviceType = EmbeddedDeviceType.fromString(getElementTextContent(deviceElement, "type"));

                ((EmbeddedDevice) device).setType(deviceType);
            }
            case PORTABLE_DEVICE -> {
                device = new PortableDevice();
                Boolean rechargeable = Boolean.valueOf(getElementTextContent(deviceElement, "rechargeable"));
                Boolean protectiveCase = Boolean.valueOf(getElementTextContent(deviceElement, "protective-case"));

                ((PortableDevice) device).setRechargeable(rechargeable);
                ((PortableDevice) device).setProtectiveCase(protectiveCase);
            }
            default -> throw new DeviceNodeException("Unexpected node for device: " + type);
        }

        device.setDeviceId(id);
        device.setVendorSite(vendorSite);
        device.setName(name);
        device.setOrigin(origin);
        device.setPurchaseDate(purchaseDate);
        device.setGroup(group);
        device.setPrice(price);
        device.setCritical(critical);
        return device;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
