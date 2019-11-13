package junit.cookbook.coffee.presentation.xsl;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

public class TransformXmlService {
    private Transformer transformer;
    private Reader stylesheetReader;
    private boolean sourceDocumentValidationEnabled;
    private Collection validationProblems = new ArrayList();

    public TransformXmlService(Reader stylesheetReader) {
        this.stylesheetReader = stylesheetReader;
    }

    public void transform(StreamSource source, Result result)
            throws TransformerException, SAXException, IOException {

        if (sourceDocumentValidationEnabled) {
            Document sourceDocument =
                    validateAndReturnSourceDocument(source);

            getTransformer().transform(
                    new DOMSource(sourceDocument),
                    result);
        } else {
            getTransformer().transform(source, result);
        }
    }

    private Document validateAndReturnSourceDocument(StreamSource source)
            throws
            SAXNotRecognizedException,
            SAXNotSupportedException,
            SAXException,
            IOException {

        DOMParser parser = new DOMParser();
        parser.setFeature(
                "http://xml.org/sax/features/validation",
                true);
        validationProblems.clear();

        parser.setErrorHandler(new ErrorHandler() {
            public void error(SAXParseException exception)
                    throws SAXException {

                validationProblems.add(exception);
            }

            public void fatalError(SAXParseException exception)
                    throws SAXException {

                validationProblems.add(exception);
            }

            public void warning(SAXParseException exception)
                    throws SAXException {
            }
        });

        parser.parse(new InputSource(source.getReader()));
        Document sourceDocument = parser.getDocument();
        return sourceDocument;
    }

    public Transformer getTransformer() {
        if (transformer == null) {
            try {
                transformer =
                        TransformerFactory
                                .newInstance()
                                .newTransformer(
                                        new StreamSource(stylesheetReader));
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(
                        "Unable to create transformer: "
                                + e.toString());
            }
        }
        return transformer;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    public void transform(
            String xmlDocumentAsString,
            Writer resultWriter)
            throws TransformerException, SAXException, IOException {

        transform(
                new StreamSource(
                        new StringReader(xmlDocumentAsString)),
                new StreamResult(resultWriter));
    }

    public void setSourceDocumentValidationEnabled(boolean sourceDocumentValidationEnabled) {
        this.sourceDocumentValidationEnabled =
                sourceDocumentValidationEnabled;
    }

    public boolean isSourceDocumentValid() {
        if (sourceDocumentValidationEnabled) {
            return validationProblems.isEmpty();
        } else
            return true;
    }

    public Collection getValidationProblems() {
        return validationProblems;
    }
}
