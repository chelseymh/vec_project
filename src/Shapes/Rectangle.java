package Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import paint_gui.Canvas;

public class Rectangle extends Shape implements FillingShape {

    public Rectangle(Canvas canvas) {
        super(canvas);
    }

    public Rectangle(List points) {
        super(points);
    }

    @Override
    public void draw(Graphics2D g) {
            //draw with reverted points if needed
            drawSanitizer();
            g.drawRect(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);
    }

    @Override
    public void fill(Graphics2D g) {
        //fill with reverted points if needed
            g.fillRect(points.get(0).x, points.get(0).y, points.get(1).x-points.get(0).x, points.get(1).y-points.get(0).y);

    }

}
