package by.latushko.xmlapp.builder;

import by.latushko.xmlapp.entity.Device;
import by.latushko.xmlapp.entity.EmbeddedDevice;
import by.latushko.xmlapp.entity.PortableDevice;
import by.latushko.xmlapp.entity.type.DeviceGroup;
import by.latushko.xmlapp.entity.type.DeviceOrigin;
import by.latushko.xmlapp.entity.type.EmbeddedDeviceType;
import by.latushko.xmlapp.exception.DeviceDataException;
import by.latushko.xmlapp.factory.BuilderFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import static by.latushko.xmlapp.factory.BuilderFactory.TypeParser.DOM;
import static org.testng.Assert.*;

public class DomDeviceBuilderTest {
    private static final String XML_DATA_FILE = "data/devices.xml";
    private AbstractDeviceBuilder builder;
    private PortableDevice portable;
    private EmbeddedDevice embedded;

    @BeforeClass
    public void setUp() {
        try {
            builder = BuilderFactory.createDeviceBuilder(DOM);
        } catch (DeviceDataException e) {
            fail(e.getMessage(), e);
        }

        portable = new PortableDevice();
        portable.setDeviceId("sn11-555555-01");
        portable.setVendorSite(Device.DEFAULT_VENDOR_SITE);
        portable.setName("Logitech keyboard");
        portable.setOrigin(DeviceOrigin.CHINA);
        portable.setPurchaseDate(YearMonth.parse("2021-11"));
        portable.setGroup(DeviceGroup.INPUT);
        portable.setPrice(new BigDecimal("15.45"));
        portable.setCritical(false);
        portable.setRechargeable(true);
        portable.setProtectiveCase(false);

        embedded = new EmbeddedDevice();
        embedded.setDeviceId("sn99-123333-12");
        embedded.setVendorSite(Device.DEFAULT_VENDOR_SITE);
        embedded.setName("Gigabyte motherboard");
        embedded.setOrigin(DeviceOrigin.JAPAN);
        embedded.setPurchaseDate(YearMonth.parse("2010-03"));
        embedded.setGroup(DeviceGroup.OTHERS);
        embedded.setPrice(new BigDecimal("243.75"));
        embedded.setCritical(true);
        embedded.setType(EmbeddedDeviceType.MOTHERBOARD);
    }

    @Test
    public void testBuildDevices() {
        Set<Device> expected = new HashSet<>();
        expected.add(portable);
        expected.add(embedded);

        try {
            builder.buildDevices(XML_DATA_FILE);
        } catch (DeviceDataException e) {
            fail(e.getMessage(), e);
        }

        Set<Device> actual = builder.getDevices();

        assertEquals(actual, expected);
    }
}