package junit.cookbook.test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NullIteratorTest extends IteratorTest {
    protected Iterator makeNoMoreElementsIterator() {
        return new Iterator() {
            public boolean hasNext() {
                return false;
            }

            public Object next() {
                throw new NoSuchElementException("NullIterator");
            }

            public void remove() {
                throw new IllegalStateException("NullIterator");
            }
        };
    }
}
