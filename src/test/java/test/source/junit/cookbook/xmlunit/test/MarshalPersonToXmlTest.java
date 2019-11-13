package junit.cookbook.xmlunit.test;

import junit.cookbook.xmlunit.Person;
import junit.cookbook.xmlunit.XmlMarshaller;
import org.custommonkey.xmlunit.XMLTestCase;

import java.io.StringWriter;

public class MarshalPersonToXmlTest extends XMLTestCase {
    public void testJbRainsberger() throws Exception {
        Person person = new Person("J. B.", "Rainsberger");
        XmlMarshaller marshaller =
                new XmlMarshaller(Person.class, "person");
        StringWriter output = new StringWriter();
        marshaller.marshal(person, output);

        String xmlDocumentAsString = output.toString();

        assertXpathExists("/person", xmlDocumentAsString);

        assertXpathEvaluatesTo(
                "J. B.",
                "/person/firstName",
                xmlDocumentAsString);

        assertXpathEvaluatesTo(
                "Rainsberger",
                "/person/lastName",
                xmlDocumentAsString);
    }

    public void testJbRainsbergerUsingEntireDocument()
            throws Exception {

        Person person = new Person("J. B.", "Rainsberger");
        XmlMarshaller marshaller =
                new XmlMarshaller(Person.class, "person");
        StringWriter output = new StringWriter();
        marshaller.marshal(person, output);

        String expectedXmlDocument =
                "<?xml version=\"1.0\" ?>\n"
                        + "<person>\n"
                        + "\t<firstName>J. B.</firstName>\n"
                        + "\t<lastName>Rainsberger</lastName>\n"
                        + "</person>\n";

        String xmlDocumentAsString = output.toString();

        assertXMLEqual(expectedXmlDocument, xmlDocumentAsString);
    }

}
