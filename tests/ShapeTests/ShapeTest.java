package ShapeTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paint_gui.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {
    Canvas canvas;

    @BeforeEach
    void setUp() {
        this.canvas = new paint_gui.Canvas();
        canvas.setBounds(0,0,400,400);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Tests Rectangle creation via points,
     * screen scaling and getCommand for rectangle
     */
    @Test
    void testRectangle() {
        List testPoints = new ArrayList<Point>();
        Point rectPoint1 = new Point();
        rectPoint1.setLocation(200,200);
        Point rectPoint2 = new Point();
        rectPoint2.setLocation(300,300);
        testPoints.add(rectPoint1);
        testPoints.add(rectPoint2);

        Shapes.Shape shape = new Shapes.Rectangle(testPoints);


        assertEquals("RECTANGLE 0.5 0.5 0.75 0.75", shape.getCommand(canvas));
    }

    /**
     * Tests Ellipse creation via points,
     * screen scaling and getCommand for ellipse
     */
    @Test
    void testEllipse() {
        List testPoints = new ArrayList<Point>();
        Point ellPoint1 = new Point();
        ellPoint1.setLocation(200,200);
        Point ellPoint2 = new Point();
        ellPoint2.setLocation(300,300);
        testPoints.add(ellPoint1);
        testPoints.add(ellPoint2);

        Shapes.Shape shape = new Shapes.Ellipse(testPoints);

        assertEquals("ELLIPSE 0.5 0.5 0.75 0.75", shape.getCommand(canvas));
    }

    /**
     * Tests Line creation via points,
     * screen scaling and getCommand for line
     */
    @Test
    void testLine() {
        List testPoints = new ArrayList<Point>();
        Point linePoint1 = new Point();
        linePoint1.setLocation(200,200);
        Point linePoint2 = new Point();
        linePoint2.setLocation(300,300);
        testPoints.add(linePoint1);
        testPoints.add(linePoint2);

        Shapes.Shape shape = new Shapes.Line(testPoints);


        assertEquals("LINE 0.5 0.5 0.75 0.75", shape.getCommand(canvas));
    }

    /**
     * Tests Plot creation via points,
     * screen scaling and getCommand for plot
     */
    @Test
    void testPlot() {
        List testPoints = new ArrayList<Point>();
        Point plotPoint = new Point();
        plotPoint.setLocation(200,200);
        testPoints.add(plotPoint);
        Shapes.Shape shape = new Shapes.Plot(testPoints);


        assertEquals("PLOT 0.5 0.5", shape.getCommand(canvas));
    }

    /**
     * Tests Polygon creation via points,
     * screen scaling and getCommand for polygon
     */
    @Test
    void testPolygon() {
        List testPoints = new ArrayList<Point>();
        Point Point1 = new Point();
        Point1.setLocation(200,0);
        Point Point2 = new Point();
        Point2.setLocation(400,200);
        Point Point3 = new Point();
        Point3.setLocation(200,400);
        Point Point4 = new Point();
        Point4.setLocation(0,200);

        testPoints.add(Point1);
        testPoints.add(Point2);
        testPoints.add(Point3);
        testPoints.add(Point4);


        Shapes.Shape shape = new Shapes.Polygon(testPoints);

        assertEquals("POLYGON 0.5 0.0 1.0 0.5 0.5 1.0 0.0 0.5", shape.getCommand(canvas));
    }


    /**
     * Give drawSanitizer points that are reversed and see if
     * it reverts them correctly
     */
    @Test
    void drawSanitizer() {
        List testPoints = new ArrayList<Point>();
        Point rectPoint1 = new Point();
        rectPoint1.setLocation(300,300);
        Point rectPoint2 = new Point();
        rectPoint2.setLocation(200,200);
        testPoints.add(rectPoint1);
        testPoints.add(rectPoint2);
        Shapes.Shape shape = new Shapes.Rectangle(testPoints);
        shape.drawSanitizer();
        assertEquals("RECTANGLE 0.5 0.5 0.75 0.75", shape.getCommand(canvas));
    }
}