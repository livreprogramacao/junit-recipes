package com.diasparsoftware.javax.servlet.http;

import javax.servlet.*;
import java.io.IOException;

public class RequestDispatcherAdapter implements RequestDispatcher {
    public void forward(
            ServletRequest request,
            ServletResponse response)
            throws ServletException, IOException {
    }

    public void include(
            ServletRequest request,
            ServletResponse response)
            throws ServletException, IOException {
    }
}
