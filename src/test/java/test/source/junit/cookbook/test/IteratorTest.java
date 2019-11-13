package junit.cookbook.test;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class IteratorTest extends TestCase {
    private Iterator noMoreElementsIterator;

    protected void setUp() throws Exception {
        noMoreElementsIterator = makeNoMoreElementsIterator();
    }

    protected abstract Iterator makeNoMoreElementsIterator()
            throws Exception;

    public void testHasNextNoMoreElements() {
        assertFalse(noMoreElementsIterator.hasNext());
    }

    public void testNextNoMoreElements() {
        try {
            noMoreElementsIterator.next();
            fail("next() worked with no more elements!");
        } catch (NoSuchElementException expected) {
        }
    }

    public void testRemoveNoMoreElements() {
        try {
            noMoreElementsIterator.remove();
            fail("remove() worked with no more elements!");
        } catch (IllegalStateException expected) {
        }
    }
}
