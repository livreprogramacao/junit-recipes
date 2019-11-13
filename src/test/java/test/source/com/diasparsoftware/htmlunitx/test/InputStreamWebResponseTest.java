package com.diasparsoftware.htmlunitx.test;

import com.diasparsoftware.htmlunitx.InputStreamWebResponse;
import com.diasparsoftware.htmlunitx.TestableWebConnection;
import com.gargoylesoftware.base.testing.EqualsTester;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

public class InputStreamWebResponseTest extends TestCase {
    public void testEquals() throws Exception {
        URL foo = new URL("http://foo");
        InputStream contentStream1 = new ByteArrayInputStream(
                new byte[0]);
        InputStream contentStream2 = new ByteArrayInputStream(
                new byte[]{0});

        InputStreamWebResponse a = new InputStreamWebResponse(
                foo, contentStream1);
        InputStreamWebResponse b = new InputStreamWebResponse(
                foo, contentStream1);
        InputStreamWebResponse c = new InputStreamWebResponse(
                foo, contentStream2);
        InputStreamWebResponse d = new InputStreamWebResponse(
                foo, contentStream1) {
        };

        new EqualsTester(a, b, c, d);

        c = new InputStreamWebResponse(new URL("file://hello"),
                contentStream1);

        new EqualsTester(a, b, c, d);
    }

    public void testEmptyHtmlTag() throws Exception {
        HtmlPage responsePage = makeHtmlPageWithContent("<html></html>");

        assertEquals("<html/>\r\n", responsePage.asXml());
    }

    public void testPageWithTitle() throws Exception {
        HtmlPage responsePage = makeHtmlPageWithContent("<html><head><title>Hello</title></head></html>");

        assertEquals("Hello", responsePage.getTitleText());
    }

    private HtmlPage makeHtmlPageWithContent(
            String contentAsString) throws Exception {

        WebClient webClient = new WebClient();

        InputStream contentAsStream = TextUtil
                .toInputStream(contentAsString);

        TestableWebConnection connection = new TestableWebConnection(
                webClient);

        URL url = new URL("http://foo");
        InputStreamWebResponse inputStreamWebResponse = new InputStreamWebResponse(
                url, contentAsStream);
        inputStreamWebResponse.setContentType("text/html");

        connection.setResponse(inputStreamWebResponse);

        WebResponse response = connection.getResponse(url,
                SubmitMethod.GET, Collections.EMPTY_LIST,
                Collections.EMPTY_MAP);

        InputStreamWebResponse expectedResponse = new InputStreamWebResponse(
                url, contentAsStream);

        assertEquals(expectedResponse, response);

        webClient.setWebConnection(connection);
        HtmlPage responsePage = (HtmlPage) webClient
                .getPage(url);
        return responsePage;
    }

    public void testGetResponseBodyWhenStreamBlowsUp()
            throws Exception {

        final IOException fakeException = new IOException(
                "This is a fake exception");

        InputStreamWebResponse response = new InputStreamWebResponse(
                new URL("http://foo"), null) {
            protected byte[] getContentAsBytes()
                    throws
                    IOException {

                throw fakeException;
            }
        };

        try {
            response.getResponseBody();
            fail("Didn't blow up when the content stream blew up?!");
        } catch (RuntimeException expected) {
        }
    }
}