package Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import paint_gui.Canvas;

public class Rectangle extends Shape implements FillingShape {
    private int x1, y1, x2, y2;

    public Rectangle(Canvas canvas) {
        super(canvas);
    }

    public Rectangle(List points) {
        super(points);
    }

    @Override
    public void draw(Graphics2D g) {
        pointSetter();
        if (x1 != 0 || x2 != 0) {
            boolean revertX = x1 < x2;
            boolean revertY = y1 < y2;
            g.drawRect(revertX ? x1 : x2, revertY ? y1 : y2, revertX ? Math.abs(x2 - x1) : Math.abs(x1 - x2),
                    revertY ? Math.abs(y2 - y1) : Math.abs(y1 - y2));
        }
    }



    @Override
    public void fill(Graphics2D g) {
        pointSetter();
        if (x1 != 0 || x2 != 0) {
            boolean revertX = x1 < x2;
            boolean revertY = y1 < y2;
            g.fillRect(revertX ? x1 : x2, revertY ? y1 : y2, revertX ? Math.abs(x2 - x1) : Math.abs(x1 - x2),
                    revertY ? Math.abs(y2 - y1) : Math.abs(y1 - y2));
        }
    }

    public void pointSetter(){
        List<Point> points= this.getPoints();
        x1=points.get(0).x;
        y1=points.get(0).y;
        x2=points.get(1).x;
        y2=points.get(1).y;
    }
}
