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
        if (points.get(0).x != 0 || points.get(1).x != 0) {
            boolean revertX = points.get(0).x < points.get(1).x;
            boolean revertY = points.get(0).y < points.get(1).y;
            g.drawRect(revertX ? points.get(0).x : points.get(1).x, revertY ? points.get(0).y : points.get(1).y, revertX ? Math.abs(points.get(1).x - points.get(0).x) : Math.abs(points.get(0).x - points.get(1).x),
                    revertY ? Math.abs(points.get(1).y - points.get(0).y) : Math.abs(points.get(0).y - points.get(1).y));
        }
    }

    @Override
    public void fill(Graphics2D g) {
        if (points.get(0).x != 0 || points.get(1).x != 0) {
            boolean revertX = points.get(0).x < points.get(1).x;
            boolean revertY = points.get(0).y < points.get(1).y;
            g.fillRect(revertX ? points.get(0).x : points.get(1).x, revertY ? points.get(0).y : points.get(1).y, revertX ? Math.abs(points.get(1).x - points.get(0).x) : Math.abs(points.get(0).x - points.get(1).x),
                    revertY ? Math.abs(points.get(1).y - points.get(0).y) : Math.abs(points.get(0).y - points.get(1).y));
        }
    }

}
