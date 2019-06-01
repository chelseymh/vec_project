package Shapes;

import java.awt.*;
import paint_gui.Canvas;

import java.awt.event.MouseEvent;
import java.util.List;

public class Plot extends Shape {
    private int x1, y1;


    public Plot(Canvas canvas) {
        super(canvas);
    }

    public Plot(List points) {
        super(points);
    }

    @Override
    public void draw(Graphics2D g) {
        pointSetter();
        g.drawLine(x1, y1, x1, y1);
    }

    public void pointSetter(){
        List<Point> points= this.getPoints();
        x1=points.get(0).x;
        y1=points.get(0).y;
    }

    @Override
    public void mousePressedAction(MouseEvent e, Canvas canvas){
        addPoint(e.getX(),e.getY());
        draw(canvas.getTheInk());
    }
}
