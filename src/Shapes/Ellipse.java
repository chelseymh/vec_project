package Shapes;

import paint_gui.Canvas;

import java.awt.*;
import java.util.List;

/**
 * Concrete child extension of Shape class. Creates an ellipse
 * shape. Implements abstract method draw by calling inbuilt
 * java oval drawing command as well as fill from the
 * FillingShape class it implements. Has two constructors,
 * one for user interactive mouse coordinate inputs and
 * a constructor for preexisting points
 */
public class Ellipse extends Shape implements FillingShape {

    /**
     * The constructor called when the ellipse is being created
     * by mouse. Passes the canvas constructor to parent
     * class Shape and lets it deal with it.
     * @param canvas the canvas on which the shape is to be
     *               drawn
     */
    public Ellipse(Canvas canvas) {
        super(canvas);
    }

    /**
     * The constructor called when the ellipse is being created
     * by existing points
     * @param points x and y coordinates of the top left of
     *               the rectangle and bottom right
     */
    public Ellipse(List points) {
        super(points);
    }

    /**
     * The ellipse implementation of draw. Calls drawSanitizer to
     * deal with coordinates not being in the format the java draw
     * component expects then draws the ellipse with the now
     * corrected points using awt library drawOval
     * @param g the Graphics2D tool the rectangle is to be drawn with
     */
    @Override
    public void draw(Graphics2D g) {
        //ellipse function takes xy coords of start followed by width and height,
        // we get this by getting the difference of our start and end coords
        //draw with reverted points if needed
            drawSanitizer();
            g.drawOval(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);
    }

    /**
     * The ellipse implementation of fill. Calls drawSanitizer to
     * deal with coordinates not being in the format the java draw
     * component expects then draws and fills the ellipse with the now
     * corrected points. Uses awt library fillOval
     * @param g the Graphics2D tool the rectangle is to be drawn and
     *          filled with
     */
    @Override
    public void fill(Graphics2D g) {
        g.fillOval(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);;

    }

}
