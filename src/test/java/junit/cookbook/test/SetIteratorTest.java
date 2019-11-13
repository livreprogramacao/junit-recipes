package junit.cookbook.test;

import java.util.Collections;
import java.util.Iterator;

public class SetIteratorTest extends IteratorTest {
    protected Iterator makeNoMoreElementsIterator() throws Exception {
        return Collections.emptyIterator();
    }
}
