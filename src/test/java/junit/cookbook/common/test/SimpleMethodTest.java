package junit.cookbook.common.test;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleMethodTest extends TestCase {
    public void testNewListIsEmpty() {
        List list = new ArrayList();
        assertEquals(0, list.size());
    }

    public void testSynchronizedListHasSameContents() {
        List list =
                Arrays.asList("Albert", "Henry", "Catherine");
        assertEquals(list, Collections.synchronizedList(list));
    }
}
