package Shapes;

import paint_gui.Canvas;
import paint_gui.guiClass;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class Ellipse extends Shape implements FillingShape {
    private int x1, y1, x2, y2;




    public Ellipse(Canvas canvas) {
        super(canvas);
    }

    public Ellipse(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics2D g) {
        pointSetter();
        //ellipse function takes xy coords of start followed by width and height,
        // we get this by getting the difference of our start and end coords
        // Revert if ellipse is reverted
        if (x1 != 0 || x2 != 0) {
            boolean revertX = x1 < x2;
            boolean revertY = y1 < y2;
            g.drawOval(revertX ? x1 : x2, revertY ? y1 : y2, revertX ? Math.abs(x2 - x1) : Math.abs(x1 - x2),
                    revertY ? Math.abs(y2 - y1) : Math.abs(y1 - y2));
        }
    }

    @Override
    public void fill(Graphics2D g) {
        pointSetter();
        if (x1 != 0 || x2 != 0) {
            boolean revertX = x1 < x2;
            boolean revertY = y1 < y2;
            g.fillOval(revertX ? x1 : x2, revertY ? y1 : y2, revertX ? Math.abs(x2 - x1) : Math.abs(x1 - x2),
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
