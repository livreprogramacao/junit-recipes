package junit.cookbook.coffee.jsp.test;

import com.diasparsoftware.java.util.Money;
import com.mockobjects.servlet.*;
import junit.cookbook.coffee.display.*;
import junit.cookbook.coffee.jsp.EachShopcartItemHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.servlet.jsp.tagext.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EachShopcartItemHandlerTest extends TestCase {
    private int shopcartSize;
    private String eachAttributeName;
    private EachShopcartItemHandler handler;
    private ShopcartBean shopcartBean;
    private MockPageContext pageContext;
    private MockJspWriter out;
    public EachShopcartItemHandlerTest(
            int shopcartSize,
            String eachAttributeName) {
        super("Shopcart size of " + shopcartSize);
        this.shopcartSize = shopcartSize;
        this.eachAttributeName = eachAttributeName;
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("EachShopcartItemHandler suite");

        for (int i = 0; i < 10; i++) {
            suite.addTest(
                    new EachShopcartItemHandlerTest(i, "item" + i));
        }

        return suite;
    }

    protected void setUp() throws Exception {
        shopcartBean = new ShopcartBean();
        handler = new EachShopcartItemHandler();

        pageContext = new MockPageContext() {
            private Map attributes = new HashMap();

            public Object getAttribute(String name) {
                return attributes.get(name);
            }

            public void setAttribute(String name, Object value) {
                attributes.put(name, value);
            }

            public void removeAttribute(String name) {
                attributes.remove(name);
            }

        };

        out = new MockJspWriter();
        pageContext.setJspWriter(out);

        handler.setPageContext(pageContext);
        handler.setParent(null);

        handler.setShopcartBean(shopcartBean);
        handler.setEach(eachAttributeName);

    }

    public void runTest() throws Exception {
        for (int i = 0; i < shopcartSize; i++) {
            shopcartBean.shopcartItems.add(
                    new ShopcartItemBean(
                            "Coffee Name " + i,
                            "00" + i,
                            i,
                            Money.dollars(i, 0)));
        }

        List shopcartItemAsList = new LinkedList(shopcartBean.shopcartItems);

        int expectedStartTagResult =
                (shopcartSize > 0) ? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;
        assertEquals(expectedStartTagResult, handler.doStartTag());

        for (int i = 0; i < shopcartSize - 1; i++) {
            assertEquals(shopcartItemAsList.get(i), getTheEachAttribute());
            assertEquals(IterationTag.EVAL_BODY_AGAIN, handler.doAfterBody());
        }

        if (shopcartSize > 0) {
            assertEquals(
                    shopcartItemAsList.get(shopcartSize - 1),
                    getTheEachAttribute());
            assertEquals(Tag.SKIP_BODY, handler.doAfterBody());
        }

        assertNull(getTheEachAttribute());
        assertEquals(Tag.EVAL_PAGE, handler.doEndTag());

        out.setExpectedData(/* Intentionally empty */ "");
        out.verify();
    }

    public Object getTheEachAttribute() {
        return pageContext.getAttribute(eachAttributeName);
    }
}
