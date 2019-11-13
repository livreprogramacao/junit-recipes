package junit.cookbook.xmlunit;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

public class XmlMarshaller {
    private Class beanClass;
    private String rootElementName;

    public XmlMarshaller(Class beanClass, String rootElementName) {
        this.beanClass = beanClass;
        this.rootElementName = rootElementName;
    }

    public void marshal(Object object, Writer output) {
        try {
            output.write("<?xml version=\"1.0\" ?>");
            output.write("<person>");

            Field[] fields = beanClass.getFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String tagName = field.getName();
                String tagValue = field.get(object).toString();
                output.write(
                        "<"
                                + tagName
                                + ">"
                                + tagValue
                                + "</"
                                + tagName
                                + ">");
            }

            output.write("</person>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}