package Exceptions;

/**
 * Checked exception to signal that the program is attempting to create
 * an incorrectly formatted shape
 *
 */
public class IllegalShapeException extends Exception {
    /**
     * Creates an <code>IllegalShapeException</code> with the shape name
     * and the problem of the format
     * @param expectedSize  expected size of shape in during creation
     * @param className name of shape throwing error
     * @param actualSize size of points actually received
     */
    public IllegalShapeException(int expectedSize, String className, int actualSize) {
        super(String.format("%1s expected %2d points, but received %3d"
                , className, expectedSize, actualSize));

    }

    /**
     * Creates an <code>IllegalShapeException</code> customized for polygon
     * @param actualSize size of points actually received
     */
    public IllegalShapeException(int actualSize) {
        super(String.format("Polygon expected at least 3 points, but received %3d"
                , actualSize));
    }
}

