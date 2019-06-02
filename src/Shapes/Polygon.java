package Shapes;
import java.util.List;
import java.awt.*;

import Exceptions.IllegalShapeException;
import paint_gui.Canvas;

//mouse handlers
import java.awt.event.MouseEvent;

/**
 * Concrete child extension of AbstractShape class. Creates a free
 * form polygon with as many points as the user desires.
 * Implements abstract method draw by calling awt
 * line drawing command for as many points as have been selected
 * by the user as well as fill from the FillingShape class it
 * implements. Has two constructors, one for user interactive
 * mouse coordinate inputs and a constructor for preexisting points.
 * Polygon overrides and implements its own mousePressedAction,
 * mouseMovedAction, and mouseReleasedAction
 */
public class Polygon extends AbstractShape implements FillingShape {

    /**
     * The constructor called when the polygon is being created
     * by mouse. Passes the canvas constructor to parent
     * class AbstractShape and lets it deal with it.
     * @param canvas the canvas on which the shape is to be
     *               drawn
     */
    public Polygon(Canvas canvas) {
        super(canvas);
    }

    /**
     * The constructor called when the polygon is being created
     * by existing points
     * @param points x and y coordinates of each point of the polygon
     */
    public Polygon(List points) throws IllegalShapeException {
        super(points);
        if (points.size()<3) {
            throw new IllegalShapeException(points.size());
        }
        points.addAll(points);
    }


    /**
     * The polygon implementation of draw. Uses awt drawLine
     * command to draw each line segment of the polygon.
     *
     * @param g the Graphics2D tool the polygon is to be drawn with
     */
    @Override
    public void draw(Graphics2D g) {
        //get the first point, initialize the first point with it
        int previousx=points.get(0).x, previousy=points.get(0).y;
        for (Point point : points.subList(1,points.size())) {
            g.drawLine(previousx, previousy, point.x, point.y);
            previousx = point.x;
            previousy=point.y;
        }
    }

    /**
     * The polygon implementation of fill. Uses awt fillPolygon
     * command to draw and fill the polygon.
     *
     * @param g the Graphics2D tool the polygon is to be drawn
     *          and filled with
     */
    @Override
    public void fill(Graphics2D g) {
        //put the points in the format fillPolygon wants
        int[] xpoints =new int[points.size()];
        int[] ypoints =new int[points.size()];
        int nPoints=0;
        for (Point point : points){
            xpoints[nPoints]=point.x;
            ypoints[nPoints]=point.y;
            nPoints++;
        }
        g.fillPolygon(xpoints, ypoints, nPoints);
    }

    //Polygon can have multiple points so requires a
    //custom point collection method
    @Override
    public void mousePressedAction(MouseEvent e){
        //Polygon is marked as done with a double click
        if (e.getClickCount() == 2) {
            Point point = new Point(e.getX(), e.getY());
            points.add(point);
            Point firstPoint = points.get(0);
            points.add(firstPoint);
            canvas.addCommand(getCommand(canvas));
            //redraw only the saved commands
            canvas.readCommands();
            //clear polygon points
            points.clear();
        //When the user selects a point
        } else {
            //if () {
                addPoint(e.getX(),e.getY());
                //Don't draw unless there is a complete pair of points
                if (points.size()>1){
                    drawPreview(e);
                }
         //   }
        }
    }

    public void mouseReleasedAction(MouseEvent e) {
//        //store mouse coords into a point object
//        Point point = new Point(e.getX(), e.getY());
//        //add that point to the polygon points list
//        points.add(point);
//        draw(canvas.getTheInk());
//        //clear the canvas of any previews
//        canvas.clean();
//        //redraw only the saved commands
//        canvas.readCommands();
//        //because our polygon isn't finished it hasn't been
//        //saved to commands so we draw manually
//        draw(canvas.getTheInk());
    }

    //more intuitive if the preview is drawn via mouse
    //moving than dragging
    public void mouseMovedAction(MouseEvent e){
        //Don't draw until at least first point is confirmed
        if (points.size()>0){
            drawPreview(e);
        }
    }
}
