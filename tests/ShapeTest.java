import Exceptions.IllegalShapeException;
import Shapes.AbstractShape;
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

        AbstractShape shape = null;
        try {
            shape = new Shapes.Rectangle(testPoints);
        } catch (IllegalShapeException e) {
            e.printStackTrace();
        }


        assertEquals("RECTANGLE 0.50 0.50 0.75 0.75", shape.getCommand(canvas));
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

        AbstractShape shape = null;
        try {
            shape = new Shapes.Ellipse(testPoints);
        } catch (IllegalShapeException e) {
            e.printStackTrace();
        }

        assertEquals("ELLIPSE 0.50 0.50 0.75 0.75", shape.getCommand(canvas));
    }

    /**
     * Tests Line creation via points,
     * screen scaling and getCommand for line
     */
    @Test
    void testLine() {
        List testPoints = new ArrayList<Point>();
        Point linePoint1 = new Point();
        linePoint1.setLocation(193,200);
        Point linePoint2 = new Point();
        linePoint2.setLocation(300,300);
        testPoints.add(linePoint1);
        testPoints.add(linePoint2);

        AbstractShape shape = null;
        try {
            shape = new Shapes.Line(testPoints);
        } catch (IllegalShapeException e) {
            e.printStackTrace();
        }


        assertEquals("LINE 0.48 0.50 0.75 0.75", shape.getCommand(canvas));
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
        AbstractShape shape = null;
        try {
            shape = new Shapes.Plot(testPoints);
        } catch (IllegalShapeException e) {
            e.printStackTrace();
        }


        assertEquals("PLOT 0.50 0.50", shape.getCommand(canvas));
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


        AbstractShape shape = null;
        try {
            shape = new Shapes.Polygon(testPoints);
        } catch (IllegalShapeException e) {
            e.printStackTrace();
        }

        assertEquals("POLYGON 0.50 0.00 1.00 0.50 0.50 1.00 0.00 0.50", shape.getCommand(canvas));
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
        AbstractShape shape = null;
        try {
            shape = new Shapes.Rectangle(testPoints);
        } catch (IllegalShapeException e) {
            e.printStackTrace();
        }
        shape.drawSanitizer();
        assertEquals("RECTANGLE 0.50 0.50 0.75 0.75", shape.getCommand(canvas));
    }

    /**
     * Tests to see if too few points passed
     * to rectangle constructor throws appropriate error
     */
    @Test
    void tooFewPointsRectConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);

            AbstractShape shape = new Shapes.Rectangle(testPoints);
        });
    }

    /**
     * Tests to see if too many points passed
     * to rectangle constructor throws appropriate error
     */
    @Test
    void tooManyPointsRectConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);
            Point Point3 = new Point();
            Point3.setLocation(200,400);
            Point Point4 = new Point();
            Point4.setLocation(0,200);

            AbstractShape shape = new Shapes.Rectangle(testPoints);
        });
    }

    /**
     * Tests to see if too few points passed
     * to ellipse constructor throws appropriate error
     */
    @Test
    void tooFewPointsEllipseConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);

            AbstractShape shape = new Shapes.Ellipse(testPoints);
        });
    }

    /**
     * Tests to see if too many points passed
     * to ellipse constructor throws appropriate error
     */
    @Test
    void tooManyPointsEllipseConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);
            Point Point3 = new Point();
            Point3.setLocation(200,400);
            Point Point4 = new Point();
            Point4.setLocation(0,200);

            AbstractShape shape = new Shapes.Ellipse(testPoints);
        });
    }

    /**
     * Tests to see if too few points passed
     * to line constructor throws appropriate error
     */
    @Test
    void tooFewPointsLineConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);

            AbstractShape shape = new Shapes.Line(testPoints);
        });
    }

    /**
     * Tests to see if too many points passed
     * to Line constructor throws appropriate error
     */
    @Test
    void tooManyPointsLineConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);
            Point Point3 = new Point();
            Point3.setLocation(200,400);
            Point Point4 = new Point();
            Point4.setLocation(0,200);

            AbstractShape shape = new Shapes.Line(testPoints);
        });
    }

    /**
     * Tests to see if incorrect number of points passed
     * to plot constructor throws appropriate error
     */
    @Test
    void tooManyPointsPlotConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);
            Point Point3 = new Point();
            Point3.setLocation(200,400);
            Point Point4 = new Point();
            Point4.setLocation(0,200);

            AbstractShape shape = new Shapes.Plot(testPoints);
        });
    }

    /**
     * Tests to see if too few points passed
     * to Polygon constructor throws appropriate error
     */
    @Test
    void tooFewPointsPolygonConstructionException() {
        assertThrows(Exception.class, () -> {
            List testPoints = new ArrayList<Point>();
            Point Point1 = new Point();
            Point1.setLocation(200,0);
            Point Point2 = new Point();
            Point2.setLocation(400,200);

            AbstractShape shape = new Shapes.Polygon(testPoints);
        });
    }
}