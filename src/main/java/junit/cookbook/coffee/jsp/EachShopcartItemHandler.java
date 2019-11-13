package junit.cookbook.coffee.jsp;

import junit.cookbook.coffee.display.ShopcartBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;

public class EachShopcartItemHandler extends TagSupport {
    private ShopcartBean shopcartBean;
    private String each;

    private Iterator items;

    public void setShopcartBean(ShopcartBean shopcartBean) {
        this.shopcartBean = shopcartBean;
    }

    public void setEach(String each) {
        this.each = each;
    }

    public int doStartTag() throws JspException {
        this.items = shopcartBean.shopcartItems.iterator();
        return setNextItemIfAny(EVAL_BODY_INCLUDE, SKIP_BODY);
    }

    public int doAfterBody() throws JspException {
        return setNextItemIfAny(EVAL_BODY_AGAIN, SKIP_BODY);
    }

    public int setNextItemIfAny(int returnIfHasNext, int returnIfNotHasNext) {
        if (items.hasNext()) {
            pageContext.setAttribute(each, items.next());
            return returnIfHasNext;
        } else {
            pageContext.removeAttribute(each);
            return returnIfNotHasNext;
        }
    }
}
