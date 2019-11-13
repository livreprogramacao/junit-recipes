package com.diasparsoftware.htmlunitx;

import com.gargoylesoftware.htmlunit.*;
import org.apache.commons.httpclient.HttpState;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestableWebConnection extends WebConnection {
    private Map responses = new HashMap();

    public TestableWebConnection(WebClient client) {
        super(client);
    }

    public WebResponse getResponse(URL url,
                                   SubmitMethod submitMethod, List parameters,
                                   Map requestHeaders) throws IOException {

        return (WebResponse) responses.get(url);
    }

    public void setResponse(WebResponse response) {
        responses.put(response.getUrl(), response);
    }

    public HttpState getStateForUrl(URL url) {
        return new HttpState();
    }

}