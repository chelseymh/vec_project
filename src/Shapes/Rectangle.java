package Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import paint_gui.Canvas;

/**
 * Concrete child extension of Shape class. Creates a rectangle
 * shape. Implements abstract method draw with in built java rect
 * drawing command as well as fill from the FillingShape class it
 * implements. Has two constructors, one for user interactive mouse
 * coordinate inputs and a constructor for preexisting points
 *
 */
public class Rectangle extends Shape implements FillingShape {

    /**
     * The constructor called when the rectangle is being created
     * by mouse. Passes the canvas constructor to parent
     * class Shape and lets it deal with it.
     * @param canvas the canvas on which the shape is to be
     *               drawn
     */
    public Rectangle(Canvas canvas) {
        super(canvas);
    }

    /**
     * The constructor called when the rectangle is being created
     * by existing points
     * @param points x and y coordinates of the top left of
     *               the rectangle and bottom right
     */
    public Rectangle(List points) {
        super(points);
    }

    /**
     * The rectangle implementation of draw. Calls drawSanitizer to
     * deal with coordinates not being in the format the java draw
     * component expects then draws the rectangle with the now
     * corrected points using awt library drawRect
     * @param g the Graphics2D tool the rectangle is to be drawn with
     */
    @Override
    public void draw(Graphics2D g) {
            //draw with reverted points if needed
            drawSanitizer();
            g.drawRect(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);
    }

    /**
     * The rectangle implementation of fill. Calls drawSanitizer to
     * deal with coordinates not being in the format the java draw
     * component expects then draws and fills the rectangle with the now
     * corrected points. Uses awt library fillRect
     * @param g the Graphics2D tool the rectangle is to be drawn and
     *          filled with
     */
    @Override
    public void fill(Graphics2D g) {
        //fill with reverted points if needed
            g.fillRect(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);

    }

}
