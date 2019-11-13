package junit.cookbook.coffee.test;

import com.diasparsoftware.javax.servlet.http.HttpSessionAdapter;
import com.mockobjects.servlet.*;
import junit.cookbook.coffee.EnsureShopperHasShopcartFilter;
import junit.framework.TestCase;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EnsureShopperHasShopcartFilterIntegrationTest
        extends TestCase {

    private HttpSession session;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;
    private EnsureShopperHasShopcartFilter filter;
    private boolean invokedFilterChainDoFilter;
    private boolean filterLogicDone;

    protected void setUp() throws Exception {
        filterLogicDone = false;
        invokedFilterChainDoFilter = false;

        session = new HttpSessionAdapter();

        request = new MockHttpServletRequest();
        request.setSession(session);

        response = new MockHttpServletResponse();

        filterChain = new MockFilterChain() {
            public void doFilter(ServletRequest request,
                                 ServletResponse response) throws IOException,
                    ServletException {

                assertTrue(
                        "Something invoked filterChain.doFilter " + "before the filter logic was done",
                        filterLogicDone);

                invokedFilterChainDoFilter = true;
            }
        };

        filter = new EnsureShopperHasShopcartFilter() {
            public void addShopcartIfNeeded(
                    ServletRequest request) {
                super.addShopcartIfNeeded(request);
                filterLogicDone = true;
            }
        };
    }

    public void testInvokesFilterChain() throws Exception {
        filter.doFilter(request, response, filterChain);
        assertTrue(invokedFilterChainDoFilter);
    }
}