package junit.cookbook.xmlunit.test;

import com.diasparsoftware.java.io.ReaderUtil;
import junit.cookbook.xmlunit.TransformXmlService;
import junit.framework.TestCase;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;

public class TransformXmlServiceTest extends TestCase {
    private TransformXmlService service;
    private boolean invokedTransform;
    private Source actualSourceUsed;
    private Result actualResultUsed;
    private Transformer fakeTransformer;
    private StringWriter resultWriter;

    protected void setUp() throws Exception {
        service =
                new TransformXmlService(
                        new FileReader(
                                new File("test/data/xsl/i-heard-you.xsl")));

        invokedTransform = false;

        fakeTransformer = new TransformerAdapter() {
            public void transform(
                    Source xmlSource,
                    Result outputTarget)
                    throws TransformerException {

                invokedTransform = true;
                actualSourceUsed = xmlSource;
                actualResultUsed = outputTarget;
            }
        };

        resultWriter = new StringWriter();
    }

    public void testPerformTransformation() throws Exception {
        StreamSource source = new StreamSource(new StringReader(""));
        Result result = new StreamResult(new StringWriter());

        service.setTransformer(fakeTransformer);
        service.transform(source, result);

        assertTrue("Did not invoke transform", invokedTransform);
        assertSame(
                "Did not use transformation source",
                source,
                actualSourceUsed);

        assertSame(
                "Did not use transformation result",
                result,
                actualResultUsed);
    }

    public void testDefaultTransformerExists() throws Exception {
        assertNotNull(service.getTransformer());
    }

    public void testDefaultTransformerUsesStylesheet()
            throws Exception {

        String xmlDocumentAsString = "<empty />";

        service.transform(xmlDocumentAsString, resultWriter);
        assertEquals(
                "Come here Watson, I want you.",
                resultWriter.toString());
    }

    public void testConvenienceTransformMethod() throws Exception {
        String xmlDocumentAsString = "An XML document";

        service.setTransformer(fakeTransformer);
        service.transform(xmlDocumentAsString, resultWriter);

        assertTrue(actualSourceUsed instanceof StreamSource);
        StreamSource actualStreamSource =
                (StreamSource) actualSourceUsed;

        String actualXmlDocumentAsString =
                ReaderUtil.getContentAsString(
                        actualStreamSource.getReader());

        assertEquals(xmlDocumentAsString, actualXmlDocumentAsString);

        assertTrue(actualResultUsed instanceof StreamResult);
        StreamResult actualStreamResult =
                (StreamResult) actualResultUsed;

        assertSame(resultWriter, actualStreamResult.getWriter());
    }

    public void testEnableDocumentValidation() throws Exception {
        String xmlDocumentFailsDtd =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\" \"http://java.sun.com/dtd/web-app_2_3.dtd\">\n"
                        + "<blah-blah-blah />";

        boolean sourceDocumentValidationOccurred;
        checkValidationOccursAsExpected(
                xmlDocumentFailsDtd,
                true,
                false,
                "Source document validation did not occur when it was enabled");

        checkValidationOccursAsExpected(
                xmlDocumentFailsDtd,
                false,
                true,
                "Source document validation occurred when it was disabled");

        String xmlDocumentPassesDtd =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\" \"http://java.sun.com/dtd/web-app_2_3.dtd\">\n"
                        + "<web-app id=\"WebApp\" />";

        checkValidationOccursAsExpected(
                xmlDocumentPassesDtd,
                true,
                true,
                "Source document validation did not occur for a valid document");
    }

    private void checkValidationOccursAsExpected(
            String xmlDocument,
            boolean enableValidation,
            boolean expectSourceDocumentToBeValid,
            String failureMessage)
            throws Exception {

        service.setSourceDocumentValidationEnabled(enableValidation);
        service.transform(xmlDocument, resultWriter);

        boolean sourceDocumentValidationOccurred =
                (service.isSourceDocumentValid()
                        == expectSourceDocumentToBeValid);

        assertTrue(failureMessage, sourceDocumentValidationOccurred);
    }
}
