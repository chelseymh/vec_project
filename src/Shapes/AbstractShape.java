package Shapes;

import Exceptions.IllegalShapeException;
import paint_gui.Canvas;
import paint_gui.Gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractShape is the abstract parent class to all the specific
 * child shape classes. Contains all the common methods used
 * by all shapes as well the mouse handler for creating the
 * shapes via mouse. The variables that the children classes
 * need access to are protected. The points of each shape are
 * stored in a List of points named <code>points</code>.
 * Because a generic constructor is used in this program the
 * <code>className</code> field informs the parent class which child owns
 * the current instance
 */
public abstract class AbstractShape {
    /**
     * The canvas on which to paint the given shape on.
     */
    protected paint_gui.Canvas canvas;
    /**
     * List of points of the individual shape. Dynamic in how many points the shape can contain.
     */
    protected List<Point> points = new ArrayList<>();
    /**
     * Records whether the shape has been drawn and the <code>MouseListener</code>s should be removed.
     */
    protected boolean done;
    /**
     * Necessary field for the testing of shapes
     */
    protected String className = this.getClass().getSimpleName();


    /**
     * Creates a new instance from a list of points. Used by FileHandler
     * and when reading commands from the string list <code>commands</code>
     * stored in Canvas
     * @param points Takes a List of the shape's points
     */
    public AbstractShape(List points) {
            this.points.addAll(points);
    }

    /**
     * This constructor is called when the user wishes to create a new shape
     * by drawing it. It instantiates the mouse listener and switches it off when
     * construction is complete via the <code>done</code> boolean field.
     * It first creates the mouse listener and the mouse motion listeners,
     * then adds them to canvas. Both listeners have action methods which can
     * be overridden when child classes have specific point collection actions that
     * deviate from the default methods. The default method listens for the first
     * mouse press and adds the coordinate at that location, then starts the
     * preview method which previews the user's dragged shape should they release at
     * that point. On release, the coordinate is save and the VEC command is stored
     * to the <code>commands</code> list in Canvas. Points are cleared for the next
     * time another shape is created.
     * @param canvas
     */
    public AbstractShape(Canvas canvas) {
        this.canvas = canvas;
        done = false;

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Gui.toggledButton.equals(className)) {
                    mousePressedAction(e);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Gui.toggledButton.equals(className)) {
                    mouseReleasedAction(e);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };

        MouseMotionListener mouseMotionListener = new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (Gui.toggledButton.equals(className)) {
                    mouseDraggedAction(e);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (Gui.toggledButton.equals(className)) {
                    mouseMovedAction(e);
                }
            }
        };

        //add listeners to the canvas
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseMotionListener);

        //if done remove the listeners
        if (done) {
            canvas.removeMouseListener(mouseListener);
            canvas.removeMouseMotionListener(mouseMotionListener);
        }
    }

    /**
     * A generic VEC command generator that generates the string command used
     * to recreate the shapes in canvas, or store them to a VEC file.
     * Takes the int saved points from the shape instance, scales with respect
     * to canvas, and saves them as float points. The class name is used to create
     * the first parameter of the VEC file.
     * @param canvas canvas is needed so the points can be scaled appropriately
     * @return
     */
    public String getCommand(Canvas canvas) {
        String command = className.toUpperCase();
        for (Point point : this.points) {
            command += String.format(" %1$3s %2$3s", (float) point.getX() / canvas.getHeight(), (float) point.getY() / canvas.getWidth());
        }
        return command;
    }

    /**
     * Handles drawing the preview of the shape. Temporarily adds preview
     * point to the points list, draws preview, then removes the preview
     * point for the user to continue dragging the mouse or confirming the
     * current drawn image
     *
     * @param e
     */

    public void drawPreview(MouseEvent e) {
        //temporarily add the preview point
        int x=points.get(points.size()-1).x;
        int y=points.get(points.size()-1).y;
        addPoint(e.getX(), e.getY());
        //Wipe and redraw
        canvas.clean();
        canvas.readCommands();
        draw(canvas.getTheInk());
        canvas.repaint();
        //remove preview points
        points.remove(points.size()-1);
        points.remove(points.size()- 1);
        addPoint(x,y);
    }

    /**
     * Needed to convert coordinate points when the user draws the shape
     * in the opposite direction from the way the draw function expects.
     * Checks to see if the points require this switch, and swaps them
     * if needed
     */
    public void drawSanitizer() {
        //if points are valid
        if (points.get(0).x != 0 || points.get(1).x != 0) {
            //Check if x needs to be reverted
            //x needs to be reverted if the first x is greater
            //than the second
            boolean revertX = points.get(0).x > points.get(1).x;
            //Check if y needs to be reverted
            //y axis increases with down direction so if first y
            //is greater than second y revert
            boolean revertY = points.get(0).y > points.get(1).y;
            //set points with reverted points if needed
            //if x needs to be reverted, swap the first and second x points
            //if not, keep them the same
            int x1 = revertX ? points.get(1).x : points.get(0).x;
            int y1 = revertY ? points.get(1).y : points.get(0).y;
            int x2 = revertX ? points.get(0).x : points.get(1).x;
            int y2 = revertY ? points.get(0).y : points.get(1).y;
            //update points
            points.get(0).setLocation(x1, y1);
            points.get(1).setLocation(x2, y2);
        }
    }

    /**
     * A short hand method to add a point to the <code>points</code>
     * list
     * @param x the x coordinate of the point in int format
     * @param y the y coordinate of the point in int format
     */
    public void addPoint(int x, int y) {
        Point point = new Point(x, y);
        points.add(point);
    }

    /**
     * Default action for what the shape does when the mouse is pressed
     * @param e Takes the mouse event so it can be used by the action
     */
    public void mousePressedAction(MouseEvent e) {
        addPoint(e.getX(), e.getY());
    }

    /**
     * Default action for what the shape does when the mouse is released
     * @param e Takes the mouse event so it can be used by the action
     */
    public void mouseReleasedAction(MouseEvent e) {
        addPoint(e.getX(), e.getY());
        canvas.addCommand(getCommand(canvas));
        canvas.clean();
        canvas.readCommands();
        points.clear();
        done = true;
    }

    /**
     * Default action for what the shape does when the mouse is moved
     * @param e Takes the mouse event so it can be used by the action
     */
    public void mouseMovedAction(MouseEvent e) {
    }

    public void mouseDraggedAction(MouseEvent e) {
        drawPreview(e);
    }

    /**
     * Abstract command the children classes are expected to implement
     * that will draw the shape the way it should be
     * @param g the ink the shape will be drawn in
     */
    public abstract void draw(Graphics2D g);
}
