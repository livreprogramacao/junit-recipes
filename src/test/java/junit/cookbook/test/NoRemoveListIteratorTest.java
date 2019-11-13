package junit.cookbook.test;

import java.util.Collections;
import java.util.Iterator;

public class NoRemoveListIteratorTest extends NoRemoveIteratorTest {
    protected Iterator makeNoMoreElementsIterator()
            throws Exception {

        return Collections.emptyIterator();
    }
}
