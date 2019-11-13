package junit.cookbook.common.test;

import junit.cookbook.common.Shape;
import junit.cookbook.common.ShapeFactory;
import junit.cookbook.common.Square;
import junit.framework.TestCase;

public class ShapeFactoryTest extends TestCase {
    public void testMakeSquare() {
        ShapeFactory factory = new ShapeFactory();
        double[] sideLengths = new double[]{5.0d};
        Shape shape = factory.makeShape("square", sideLengths);
        assertTrue(shape instanceof Square);
        Square expected = new Square(5.0d);
        assertEquals(expected, shape);
    }

    public void testChooseMakeSquare() {
        SpyShapeMaker spyShapeMaker = new SpyShapeMaker();

        ShapeFactory factory = new ShapeFactory(spyShapeMaker);
        double[] dummyParameters = new double[0];
        factory.makeShape("square", dummyParameters);

        assertEquals(1, spyShapeMaker.getShapesMadeCount());
        assertEquals(1, spyShapeMaker.getSquaresMadeCount());
    }
}
