package Shapes;

import Exceptions.IllegalShapeException;
import paint_gui.Canvas;

import java.awt.*;
import java.util.List;

/**
 * Concrete child extension of AbstractShape class. Creates an ellipse
 * shape. Implements abstract method draw by calling awt
 * oval drawing command as well as fill from the
 * FillingShape class it implements. Has two constructors,
 * one for user interactive mouse coordinate inputs and
 * a constructor for preexisting points.
 */
public class Ellipse extends AbstractShape implements FillingShape {

    /**
     * The constructor called when the ellipse is being created
     * by mouse. Passes the canvas constructor to parent
     * class AbstractShape and lets it deal with it.
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
     *               the ellipse if it was enclosed with a
     *              rectangle and bottom right corner
     */
    public Ellipse(List points) throws IllegalShapeException {
        super(points);
        if (points.size()!=2){
            throw new IllegalShapeException(2,className, points.size());
        }
    }

    /**
     * The ellipse implementation of draw. Calls drawSanitizer to
     * deal with coordinates not being in the format awt
     * expects then draws the ellipse with the now
     * corrected points using awt library drawOval
     * @param g the Graphics2D tool the ellipse is to be drawn with
     */
    @Override
    public void draw(Graphics2D g) {
        //draw with reverted points if needed
            drawSanitizer();
            g.drawOval(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);
    }

    /**
     * The ellipse implementation of fill. Calls drawSanitizer to
     * deal with coordinates not being in the format the java draw
     * component expects then draws and fills the ellipse with the now
     * corrected points. Uses awt command fillOval
     * @param g the Graphics2D tool the rectangle is to be drawn and
     *          filled with
     */
    @Override
    public void fill(Graphics2D g) {
        drawSanitizer();
        g.fillOval(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);;

    }

}
