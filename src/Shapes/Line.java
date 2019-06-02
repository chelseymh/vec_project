package Shapes;

import java.awt.*;
import paint_gui.Canvas;

//mouse handlers
import java.util.List;

public class Line extends Shape {

    public Line(Canvas canvas) {
        super(canvas);
    }

    public Line(List points) {
        super(points);
    }

    public void draw(Graphics2D g) {
        g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
    }
}
