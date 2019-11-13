package junit.cookbook.jsp.test;

import com.diasparsoftware.java.util.Money;
import com.diasparsoftware.javax.servlet.ForwardingServlet;
import com.diasparsoftware.util.junit.GoldMasterFile;
import com.meterware.servletunit.*;
import junit.cookbook.coffee.display.*;
import junit.cookbook.coffee.presentation.test.JspTestCase;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class RenderShopcartJspTest extends JspTestCase {
    private ShopcartBean shopcartBean;
    private ServletRunner servletRunner;
    private ServletUnitClient client;

    protected void setUp() throws Exception {
        shopcartBean = new ShopcartBean();

        servletRunner = new ServletRunner(
                getWebContentPath("/WEB-INF/web.xml"),
                "/coffeeShop");
        servletRunner.registerServlet("/forward",
                ForwardingServlet.class
                        .getName());

        client = servletRunner.newClient();
    }

    public void testEmptyShopcart() throws Exception {
        checkShopcartPageAgainst(new File("test/gold",
                "emptyShopcart-master.txt"));
    }

    public void testOneItemInShopcart() throws Exception {
        shopcartBean.shopcartItems.add(new ShopcartItemBean(
                "Sumatra", "762", 5, Money.dollars(7, 50)));

        checkShopcartPageAgainst(new File("test/gold",
                "oneItemInShopcart-master.txt"));
    }

    private void checkShopcartPageAgainst(File goldMasterFile)
            throws Exception {

        String responseText = getActualShopcartPageContent();
        new GoldMasterFile(goldMasterFile).check(responseText);
    }

    private String getActualShopcartPageContent()
            throws Exception {

        InvocationContext invocationContext = client
                .newInvocation("http://localhost/coffeeShop/forward");

        ForwardingServlet servlet = (ForwardingServlet) invocationContext
                .getServlet();

        servlet.setForwardUri("/shopcart.jsp");

        HttpServletRequest request = invocationContext
                .getRequest();

        request.setAttribute("shopcartDisplay", shopcartBean);
        servlet.service(request, invocationContext
                .getResponse());
        return invocationContext.getServletResponse().getText();
    }

}