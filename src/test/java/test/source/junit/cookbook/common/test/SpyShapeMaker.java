package junit.cookbook.common.test;

import junit.cookbook.common.ShapeMaker;
import junit.cookbook.common.Square;

public class SpyShapeMaker extends ShapeMaker {
    private int squaresMade = 0;

    public int getShapesMadeCount() {
        return squaresMade;
    }

    public int getSquaresMadeCount() {
        return squaresMade;
    }

    public Square makeSquare(double sideLength) {
        squaresMade++;
        return super.makeSquare(sideLength);
    }
}
