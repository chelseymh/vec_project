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
        // Revert if ellipse is reverted
        if (points.get(0).x != 0 || points.get(1).x != 0) {
            boolean revertX = points.get(0).x < points.get(1).x;
            boolean revertY = points.get(0).y < points.get(1).y;
            g.drawOval(revertX ? points.get(0).x : points.get(1).x, revertY ? points.get(0).y : points.get(1).y, revertX ? Math.abs(points.get(1).x - points.get(0).x) : Math.abs(points.get(0).x - points.get(1).x),
                    revertY ? Math.abs(points.get(1).y - points.get(0).y) : Math.abs(points.get(0).y - points.get(1).y));
        }
    }

    @Override
    public void fill(Graphics2D g) {
        if (points.get(0).x != 0 || points.get(1).x != 0) {
            boolean revertX = points.get(0).x < points.get(1).x;
            boolean revertY = points.get(0).y < points.get(1).y;
            g.fillOval(revertX ? points.get(0).x : points.get(1).x, revertY ? points.get(0).y : points.get(1).y, revertX ? Math.abs(points.get(1).x - points.get(0).x) : Math.abs(points.get(0).x - points.get(1).x),
                    revertY ? Math.abs(points.get(1).y - points.get(0).y) : Math.abs(points.get(0).y - points.get(1).y));
        }
    }

}
