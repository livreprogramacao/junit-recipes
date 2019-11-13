package junit.cookbook.coffee.test;

import com.diasparsoftware.javax.servlet.http.HttpSessionAdapter;
import com.mockobjects.servlet.*;
import junit.cookbook.coffee.EnsureShopperHasShopcartFilter;
import junit.cookbook.coffee.model.ShopcartModel;
import junit.framework.TestCase;

public class EnsureShopperHasShopcartFilterLogicTest extends
        TestCase {
    private FakeHttpSession session;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private EnsureShopperHasShopcartFilter filter;

    protected void setUp() throws Exception {
        session = new FakeHttpSession();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filter = new EnsureShopperHasShopcartFilter();

        request.setSession(session);
    }

    public void testAlreadyHasShopcart() throws Exception {
        ShopcartModel shopcartModel = new ShopcartModel();
        session.setAttribute("shopcartModel", shopcartModel);

        filter.addShopcartIfNeeded(request);

        assertSame(shopcartModel, request.getSession(true)
                .getAttribute("shopcartModel"));
    }

    public void testEmptySession() throws Exception {
        filter.addShopcartIfNeeded(request);
        assertNotNull(session.shopcartModelAttribute);
        assertTrue(session.shopcartModelAttribute.isEmpty());
    }

    public void testNoSession() throws Exception {
        request.setExpectedCreateSession(true);

        filter.addShopcartIfNeeded(request);
        assertNotNull(session.shopcartModelAttribute);
        assertTrue(session.shopcartModelAttribute.isEmpty());
    }

    public static class FakeHttpSession extends
            HttpSessionAdapter {
        public ShopcartModel shopcartModelAttribute;

        public Object getAttribute(String name) {
            return shopcartModelAttribute;
        }

        public void setAttribute(String name, Object value) {
            shopcartModelAttribute = (ShopcartModel) value;
        }
    }
}