package by.latushko.xmlapp.validator;

import by.latushko.xmlapp.exception.DeviceDataException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DeviceXmlValidator {
    private static final String SCHEMA_PATH = "schema/devices-schema.xsd";

    public static boolean validate(String file) throws DeviceDataException {
        ClassLoader loader = DeviceXmlValidator.class.getClassLoader();
        URL resource = loader.getResource(SCHEMA_PATH);
        File schemaLocation = new File(resource.getFile());

        URL resourceFile = loader.getResource(file);
        String filePath = new File(resourceFile.getFile()).getPath();

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try{
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filePath);
            validator.validate(source);
        }  catch (IOException ex) {
            throw new DeviceDataException("It's impossible to read or validate file data: " + file, ex);
        } catch (SAXException ex) {
            return false;
        }

        return true;
    }
}
