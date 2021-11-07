package by.latushko.xmlapp.validator;

import by.latushko.xmlapp.exception.DeviceDataException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DeviceXmlValidatorTest {
    private DeviceXmlValidator deviceXmlValidator;
    private static final String XML_DATA_CORRECT_FILE = "data/devices.xml";
    private static final String XML_DATA_INCORRECT_FILE = "data/devices-wrong.xml";

    @BeforeClass
    public void setUp() {
        deviceXmlValidator = new DeviceXmlValidator();
    }

    @Test
    public void testValidateTrue() {
        boolean actual = false;
        try {
            actual = deviceXmlValidator.validate(XML_DATA_CORRECT_FILE);
        } catch (DeviceDataException e) {
            fail(e.getMessage(), e);
        }

        assertTrue(actual);
    }

    @Test
    public void testValidateFalse() {
        boolean actual = false;
        try {
            actual = deviceXmlValidator.validate(XML_DATA_INCORRECT_FILE);
        } catch (DeviceDataException e) {
            fail(e.getMessage(), e);
        }

        assertFalse(actual);
    }
}