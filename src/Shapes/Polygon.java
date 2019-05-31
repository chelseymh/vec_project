package Shapes;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import paint_gui.Canvas;

//mouse handlers
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Polygon extends Shape implements FillingShape {
    private List<Point> points= new ArrayList<Point>();


    public Polygon(Canvas canvas) {
        super(canvas);
    }

    public Polygon(List points) {
        super(points);
        this.points.addAll(points);
    }

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

    @Override
    public void mousePressedAction(MouseEvent e, Canvas canvas){
        System.out.println("Mouse has been pressed in poly class");
        //Polygon is marked as done with a double click
        if (e.getClickCount() == 2) {
            Point point = new Point(e.getX(), e.getY());
            points.add(point);
            Point firstPoint = points.get(0);
            points.add(firstPoint);
            canvas.addCommand(getCommand(canvas));
            //redraw only the saved commands
            canvas.readCommands();
            //clear parent points
            getPoints().clear();
            //clear polygon points
            this.points.clear();

        } else {
            Point point = new Point(e.getX(), e.getY());
            points.add(point);
            //The second click in a polygon line segment
        }
    }

    public void mouseReleasedAction(MouseEvent e, Canvas canvas) {
        //store mouse coords into a point object
        Point point = new Point(e.getX(), e.getY());
        //add that point to the polygon points list
        points.add(point);
        draw(canvas.getTheInk());
        //clear the canvas of any previews
        canvas.clean();
        //redraw only the saved commands
        canvas.readCommands();
        //because our polygon isn't finished it hasn't been
        //saved to commands so we draw manually
        draw(canvas.getTheInk());
    }

    public void mouseMovedAction(MouseEvent e, Canvas canvas){
        //Don't draw until first point is confirmed
        setPoints(points);
        if (points.size()>1){
            drawPreview(e, canvas);
            System.out.println("points = " + points);
        }
    }

    public void pointSetter(){
        this.points= getPoints();
    }


}
