package by.latushko.xmlapp.builder.handler;

import by.latushko.xmlapp.builder.tag.DeviceXmlTag;
import by.latushko.xmlapp.entity.Device;
import by.latushko.xmlapp.entity.EmbeddedDevice;
import by.latushko.xmlapp.entity.PortableDevice;
import by.latushko.xmlapp.entity.type.DeviceGroup;
import by.latushko.xmlapp.entity.type.DeviceOrigin;
import by.latushko.xmlapp.entity.type.EmbeddedDeviceType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DeviceHandler extends DefaultHandler {
    private static final Logger logger = LogManager.getLogger();
    private Set<Device> devices;
    private EnumSet<DeviceXmlTag> tagWithTextContent;
    private Device currentDevice;
    private DeviceXmlTag currentTag;

    public DeviceHandler() {
        devices = new HashSet<>();
        tagWithTextContent = EnumSet.range(DeviceXmlTag.NAME, DeviceXmlTag.TYPE);
    }

    public Set<Device> getDevices() {
        return devices;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if(DeviceXmlTag.EMBEDDED_DEVICE.getValue().equals(qName) || DeviceXmlTag.PORTABLE_DEVICE.getValue().equals(qName)) {
            currentDevice = DeviceXmlTag.EMBEDDED_DEVICE.getValue().equals(qName) ? new EmbeddedDevice() : new PortableDevice();

            String deviceId = attributes.getValue(0);
            String vendorSite = attributes.getValue(1);

            currentDevice.setDeviceId(deviceId);
            currentDevice.setVendorSite(vendorSite == null ? Device.DEFAULT_VENDOR_SITE : vendorSite);
        } else {
            DeviceXmlTag temp = DeviceXmlTag.valueOf(formatName(qName.toUpperCase()));
            if (tagWithTextContent.contains(temp)) {
                currentTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if(DeviceXmlTag.EMBEDDED_DEVICE.getValue().equals(qName) || DeviceXmlTag.PORTABLE_DEVICE.getValue().equals(qName)) {
            logger.log(Level.INFO, "Device id: {} has been built by SAX builder", currentDevice.getDeviceId());
            devices.add(currentDevice);
            currentDevice = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentTag != null) {
            switch (currentTag) {
                case NAME -> currentDevice.setName(data);
                case ORIGIN -> currentDevice.setOrigin(DeviceOrigin.fromString(data));
                case PURCHASE_DATE -> currentDevice.setPurchaseDate(YearMonth.parse(data));
                case GROUP -> currentDevice.setGroup(DeviceGroup.fromString(data));
                case PRICE -> currentDevice.setPrice(new BigDecimal(data));
                case CRITICAL -> currentDevice.setCritical(Boolean.valueOf(data));
                case RECHARGEABLE -> {
                    PortableDevice device = (PortableDevice) currentDevice;
                    device.setRechargeable(Boolean.valueOf(data));
                }
                case PROTECTIVE_CASE -> {
                    PortableDevice device = (PortableDevice) currentDevice;
                    device.setProtectiveCase(Boolean.valueOf(data));
                }
                case TYPE -> {
                    EmbeddedDevice device = (EmbeddedDevice) currentDevice;
                    device.setType(EmbeddedDeviceType.fromString(data));
                }
                default -> throw new EnumConstantNotPresentException(
                        currentTag.getDeclaringClass(), currentTag.name());
            }
        }
        currentTag = null;
    }

    private String formatName(String value) {
        return value.replace("-", "_");
    }
}
