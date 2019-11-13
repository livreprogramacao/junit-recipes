package junit.cookbook.coffee.presentation.test;

import com.meterware.httpunit.*;
import com.meterware.servletunit.*;
import junit.cookbook.coffee.CoffeeShopController;
import junit.cookbook.coffee.display.ShopcartBean;
import junit.cookbook.coffee.test.CoffeeShopFixture;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class ShopcartJspDataTest extends CoffeeShopFixture {
    protected void setUp() throws Exception {
        assertTrue("Cannot find web deployment descriptor",
                new File(getWebDeploymentDescriptorPath())
                        .exists());
    }

    public void testControllerProvidesShopcartBean()
            throws Exception {

        fail("This test is broken. I need an answer from the "
                + "ServletUnit mailing list to help me fix it. "
                + "There is a servlet/URL mapping for /coffee, "
                + "but the HTTP client complains that there is "
                + "no action mapping to the request for /coffee.");

        ServletRunner servletRunner = new ServletRunner(
                getWebDeploymentDescriptorPath());

        servletRunner
                .registerServlet("Coffee Shop Controller",
                        CoffeeShopController.class
                                .getName());

        ServletUnitClient client = servletRunner.newClient();

        WebRequest addToShopcartRequest = new PostMethodWebRequest(
                "http://localhost:9080/coffee");
        addToShopcartRequest.setParameter("displayShopcart",
                "shopcart");

        InvocationContext invocationContext = client
                .newInvocation(addToShopcartRequest);

        HttpServletRequest httpServletRequest = invocationContext
                .getRequest();

        client.getResponse(addToShopcartRequest);

        Object shopcartDisplayAttribute = httpServletRequest
                .getAttribute("shopcartDisplay");

        assertNotNull(shopcartDisplayAttribute);
        assertTrue(shopcartDisplayAttribute instanceof ShopcartBean);
    }

    private InputStream getWebXmlAsStream() {
        StringBuffer webXml = new StringBuffer();
        webXml
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        webXml
                .append("<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\" \"http://java.sun.com/dtd/web-app_2_3.dtd\">");
        webXml.append("<web-app id=\"WebApp\">");
        webXml
                .append(" <display-name>CoffeeShopWeb</display-name>");
        webXml.append(" <servlet>");
        webXml
                .append("     <servlet-name>CoffeeShopController</servlet-name>");
        webXml
                .append("     <display-name>CoffeeShopController</display-name>");
        webXml
                .append("     <servlet-class>junit.cookbook.coffee.CoffeeShopController</servlet-class>");
        webXml.append("     <init-param>");
        webXml
                .append("         <param-name>Sumatra</param-name>");
        webXml
                .append("         <param-value>$7.50</param-value>");
        webXml.append("     </init-param>");
        webXml.append("     <init-param>");
        webXml
                .append("         <param-name>Huehuetenango</param-name>");
        webXml
                .append("         <param-value>$7.75</param-value>");
        webXml.append("     </init-param>");
        webXml.append("     <init-param>");
        webXml
                .append("         <param-name>Special Blend</param-name>");
        webXml
                .append("         <param-value>$8.40</param-value>");
        webXml.append("     </init-param>");
        webXml.append(" </servlet>");
        webXml.append(" <servlet-mapping>");
        webXml
                .append("     <servlet-name>CoffeeShopController</servlet-name>");
        webXml
                .append("     <url-pattern>/coffee</url-pattern>");
        webXml.append(" </servlet-mapping>");
        webXml.append(" <welcome-file-list>");
        webXml
                .append("     <welcome-file>index.html</welcome-file>");
        webXml
                .append("     <welcome-file>index.htm</welcome-file>");
        webXml
                .append("     <welcome-file>index.jsp</welcome-file>");
        webXml
                .append("     <welcome-file>default.html</welcome-file>");
        webXml
                .append("     <welcome-file>default.htm</welcome-file>");
        webXml
                .append("     <welcome-file>default.jsp</welcome-file>");
        webXml.append(" </welcome-file-list>");
        webXml.append("</web-app>");
        return new ByteArrayInputStream(webXml.toString()
                .getBytes());
    }
}