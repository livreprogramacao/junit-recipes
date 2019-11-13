package junit.cookbook.coffee.deployment.test;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

public class StringEntityResolver implements EntityResolver {
    private String entityText;

    public StringEntityResolver(String entityText) {
        this.entityText = entityText;
    }

    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {

        return new InputSource(new StringReader(entityText));
    }
}
