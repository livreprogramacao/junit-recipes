package com.diasparsoftware.htmlunitx.test;

import com.diasparsoftware.htmlunitx.InputStreamWebResponse;
import com.diasparsoftware.htmlunitx.TestableWebConnection;
import com.gargoylesoftware.htmlunit.*;
import junit.framework.TestCase;

import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

public class TestableWebConnectionTest extends TestCase {
    public void testEmptyResponse() throws Exception {
        String contentAsString = "";
        InputStream contentAsStream = TextUtil
                .toInputStream(contentAsString);

        TestableWebConnection connection = new TestableWebConnection(
                new WebClient());

        URL url = new URL("http://foo");
        InputStreamWebResponse inputStreamWebResponse = new InputStreamWebResponse(
                url, contentAsStream);
        inputStreamWebResponse.setContentType("text/plain");

        connection.setResponse(inputStreamWebResponse);

        WebResponse response = connection.getResponse(url,
                SubmitMethod.GET, Collections.EMPTY_LIST,
                Collections.EMPTY_MAP);

        InputStreamWebResponse expectedResponse = new InputStreamWebResponse(
                url, contentAsStream);
        assertEquals(expectedResponse, response);
    }

    public void testNonEmptyPlainResponse() throws Exception {
        String contentAsString = "Come here, Watson; I want you.";
        InputStream contentAsStream = TextUtil
                .toInputStream(contentAsString);

        TestableWebConnection connection = new TestableWebConnection(
                new WebClient());

        URL url = new URL("http://foo");
        InputStreamWebResponse inputStreamWebResponse = new InputStreamWebResponse(
                url, contentAsStream);
        inputStreamWebResponse.setContentType("text/plain");

        connection.setResponse(inputStreamWebResponse);

        WebResponse response = connection.getResponse(url,
                SubmitMethod.GET, Collections.EMPTY_LIST,
                Collections.EMPTY_MAP);

        InputStreamWebResponse expectedResponse = new InputStreamWebResponse(
                url, contentAsStream);
        assertEquals(expectedResponse, response);
    }
}