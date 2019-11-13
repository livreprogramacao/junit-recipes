package com.diasparsoftware.htmlunitx.test;

import com.diasparsoftware.htmlunitx.InputStreamWebResponse;
import com.diasparsoftware.htmlunitx.TestableWebResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

public class EmptyInputStreamWebResponseTest
        extends AbstractWebResponseTestCase {

    private byte[] contentAsBytes = new byte[0];
    private ByteArrayInputStream contentAsStream =
            new ByteArrayInputStream(contentAsBytes);

    protected TestableWebResponse makeActualWebResponse()
            throws Exception {

        return new InputStreamWebResponse(
                new URL("http://foo"),
                contentAsStream);
    }

    protected byte[] getExpectedResponseBody() throws Exception {
        return contentAsBytes;
    }

    protected InputStream getExpectedContentAsStream()
            throws Exception {

        return contentAsStream;
    }

    protected String getExpectedContentAsString() throws Exception {
        return "";
    }
}
