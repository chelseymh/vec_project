package Shapes;

import java.awt.*;
import paint_gui.Canvas;

//mouse handlers
import java.util.List;

/**
 * Concrete child extension of AbstractShape class. Creates a line
 * shape. Implements abstract method draw with awt line
 * drawing command. Has two constructors, one for user
 * interactive mouse coordinate inputs and a constructor
 * for preexisting points.
 */
public class Line extends AbstractShape {

    /**
     * The constructor called when the line is being created
     * by mouse. Passes the canvas constructor to parent
     * class AbstractShape and lets it deal with it.
     * @param canvas the canvas on which the shape is to be
     *               drawn
     */
    public Line(Canvas canvas) {
        super(canvas);
    }

    /**
     * The constructor called when the line is being created
     * by existing points
     * @param points x and y coordinates of the beginning of
     *               the line and end point
     */
    public Line(List points) {
        super(points);
    }

    /**
     * The line implementation of draw. Draws with awt library
     * drawLine
     * @param g the Graphics2D tool the line is to be drawn with
     */
    public void draw(Graphics2D g) {
        g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
    }
}
