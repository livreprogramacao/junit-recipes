package com.diasparsoftware.java.util.test;

import com.diasparsoftware.java.util.DateUtil;
import junit.framework.TestCase;

public class DateUtilTest extends TestCase {
    public void testGetYear() throws Exception {
        assertEquals(
                2004,
                DateUtil.getYear(DateUtil.makeDate(2004, 6, 1)));
    }
}
