package Shapes;

import paint_gui.Canvas;
import paint_gui.Gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
    protected paint_gui.Canvas canvas;
    protected List<Point> points = new ArrayList<Point>();
    protected boolean done;
    private String className = this.getClass().getSimpleName();


    public Shape() {
    }

    public Shape(List points) {
        this.points.addAll(points);
    }

    public Shape(Canvas canvas) {
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

        canvas.addMouseListener(mouseListener);


        MouseMotionListener mouseMotionListener = new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (Gui.toggledButton.equals(className)) {
                    //drawPreview(e, canvas);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (Gui.toggledButton.equals(className)) {
                    mouseMovedAction(e);
                }
            }
        };

        canvas.addMouseMotionListener(mouseMotionListener);

        if (done) {
            canvas.removeMouseListener(mouseListener);
            canvas.removeMouseMotionListener(mouseMotionListener);
        }
    }

    public String getCommand(Canvas canvas) {
        String command = className.toUpperCase();
        for (Point point : this.points) {
            command += String.format(" %1$.2f %2$.2f", (float) point.getX() / canvas.getHeight(), (float) point.getY() / canvas.getWidth());
        }
        return command;
    }

    public void drawPreview(MouseEvent e, Canvas canvas) {
        //temporarily add the preview point
        addPoint(e.getX(), e.getY());
        //Wipe and redraw
        canvas.clean();
        canvas.readCommands();
        draw(canvas.getTheInk());
        canvas.repaint();
        //remove last element added which is the preview point
        points.remove(points.size() - 1);
    }

    public void addPoint(int x, int y) {
        Point point = new Point(x, y);
        points.add(point);
    }

    public void mousePressedAction(MouseEvent e) {
        addPoint(e.getX(), e.getY());
    }

    public void mouseReleasedAction(MouseEvent e) {
        addPoint(e.getX(), e.getY());
        canvas.addCommand(getCommand(canvas));
        canvas.clean();
        canvas.readCommands();
        points.clear();
        done = true;
    }

    public void mouseMovedAction(MouseEvent e) {
    }

    public abstract void draw(Graphics2D g);

}
