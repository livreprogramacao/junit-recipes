package junit.cookbook.common.test;

import junit.cookbook.common.ShapeMaker;
import junit.cookbook.common.Square;
import junit.framework.TestCase;

public class ShapeMakerTest extends TestCase {
    public void testMakeSquare() {
        ShapeMaker shapeMaker = new ShapeMaker();
        Square expected = new Square(5.0d);
        assertEquals(expected, shapeMaker.makeSquare(5.0d));
    }
}
