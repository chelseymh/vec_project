package Shapes.ShapeTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paint_gui.Canvas;
import paint_gui.Gui;

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

    @Test
    void getCommand() {
        List testPoints = new ArrayList<Point>();
        Point rectPoint1 = new Point();
        rectPoint1.setLocation(200,200);
        Point rectPoint2 = new Point();
        rectPoint1.setLocation(300,300);
        testPoints.add(rectPoint1);
        testPoints.add(rectPoint2);

        Shapes.Shape shape = new Shapes.Rectangle(testPoints);

        //calculate with canvas bounds
        System.out.println(canvas.getSize());
//        assertEquals(400, canvas.getSize().height);
//        assertEquals(400, canvas.getSize().width);
        assertEquals("RECTANGLE 0.5 0.5 0.75 0.75", shape.getCommand(canvas));
    }

    @Test
    void drawPreview() {
    }

    @Test
    void drawSanitizer() {
        

    }

    @Test
    void addPoint() {
    }

    @Test
    void mousePressedAction() {
    }

    @Test
    void mouseReleasedAction() {
    }

    @Test
    void mouseMovedAction() {
    }

    @Test
    void draw() {
    }
}