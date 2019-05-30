package Shapes;

import java.awt.*;
import paint_gui.Canvas;
import java.util.List;

public class Plot extends Shape {
    private int x1, y1;
    private Canvas canvas;

    public Plot(Canvas canvas) {
        super(canvas);
        this.canvas=canvas;
    }

    public Plot(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics2D g) {
        pointSetter();
        g.drawLine(x1, y1, x1, y1);
    }

    @Override
    public void fill(Graphics2D g) {

    }

    @Override
    public String getCommand(){
        return String.format("PLOT %1$.2f %2$.2f", (float)x1/canvas.getHeight(), (float)y1/canvas.getWidth());
    }

    public void pointSetter(){
        List<Point> points= this.getPoints();
        x1=points.get(0).x;
        y1=points.get(0).y;
        x1=points.get(1).x;
        y1=points.get(1).y;
    }
}
