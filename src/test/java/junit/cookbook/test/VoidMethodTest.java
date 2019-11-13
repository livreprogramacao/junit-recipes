package junit.cookbook.test;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VoidMethodTest extends TestCase {
    public void testListAdd() {
        List list = new ArrayList();
        assertFalse(list.contains("hello"));
        list.add("hello");
        assertTrue(list.contains("hello"));
    }

    public void testLoadProperties() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("application.properties"));
        assertEquals("jbrains", properties.getProperty("username"));
        assertEquals("jbra1ns", properties.getProperty("password"));
    }

    public void testListContains() {
        List list = new ArrayList();
        assertFalse(list.contains(new Integer(762)));
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        list.add(new Integer(762));
        assertTrue(list.contains(new Integer(762)));
        assertEquals(1, list.size());
    }
}
