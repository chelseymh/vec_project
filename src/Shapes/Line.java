package Shapes;

import java.awt.*;
import paint_gui.Canvas;
import paint_gui.guiClass;

//mouse handlers
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class Line extends Shape {
    private int x1, y1, x2, y2;

    public Line(Canvas canvas) {
        super(canvas);
    }

    public Line(List points) {
        super(points);
    }

    public void draw(Graphics2D g) {
        pointSetter();
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fill(Graphics2D g) {

    }

    public void pointSetter(){
        List<Point> points= this.getPoints();
        x1=points.get(0).x;
        y1=points.get(0).y;
        x2=points.get(1).x;
        y2=points.get(1).y;
    }


}
