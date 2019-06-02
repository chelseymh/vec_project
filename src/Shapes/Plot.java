package Shapes;

import java.awt.*;

import Exceptions.IllegalShapeException;
import paint_gui.Canvas;

import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Concrete child extension of AbstractShape class. Creates a plot
 * shape. This is a single dot so has its own implementation
 * of several AbstractShape methods. Overrides mousePressedAction
 * and mouseReleasedAction. Has two constructors,
 * one for user interactive mouse coordinate inputs and
 * a constructor for preexisting points.
 */
public class Plot extends AbstractShape {

    /**
     * The constructor called when the plot is being created
     * by mouse. Passes the canvas constructor to parent
     * class AbstractShape and lets it deal with it.
     * @param canvas the canvas on which the shape is to be
     *               drawn
     */
    public Plot(Canvas canvas) {
        super(canvas);
    }


    /**
     * The constructor called when the plot is being created
     * by existing points
     * @param points x and y coordinates of the plot
     */
    public Plot(List points) throws IllegalShapeException {
        super(points);
        if (points.size()!=1){
            throw new IllegalShapeException(1,className, points.size());
        }
    }

    /**
     * The plot implementation of draw. Uses the awt drawLine
     * command but uses the same coordinates for beginning
     * and end point so a dot is drawn instead of a full line
     * @param g the Graphics2D tool the plot is to be drawn with
     */
    @Override
    public void draw(Graphics2D g) {
        g.drawLine(points.get(0).x, points.get(0).y, points.get(0).x, points.get(0).y);
    }
    //Draw the single point
    @Override
    public void mousePressedAction(MouseEvent e){
        addPoint(e.getX(),e.getY());
        canvas.addCommand(getCommand(canvas));
        canvas.clean();
        canvas.readCommands();
        points.clear();
    }

    //Plot is a single click shape so no release action
    @Override
    public void mouseReleasedAction(MouseEvent e) {
    }
    //Plot is a single click shape so no drag action
    public void mouseDraggedAction(MouseEvent e) {}


}
