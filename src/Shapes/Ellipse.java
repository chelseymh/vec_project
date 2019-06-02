package Shapes;

import paint_gui.Canvas;

import java.awt.*;
import java.util.List;

public class Ellipse extends Shape implements FillingShape {

    public Ellipse(Canvas canvas) {
        super(canvas);
    }

    public Ellipse(List points) {
        super(points);
    }

    @Override
    public void draw(Graphics2D g) {
        //ellipse function takes xy coords of start followed by width and height,
        // we get this by getting the difference of our start and end coords
        //draw with reverted points if needed
            drawSanitizer();
            g.drawOval(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
    }

    @Override
    public void fill(Graphics2D g) {
        g.fillOval(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);

    }

}
