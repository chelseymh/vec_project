package Shapes;

import java.awt.*;
import paint_gui.Canvas;

import java.awt.event.MouseEvent;
import java.util.List;

public class Plot extends Shape {
    public Plot(Canvas canvas) {
        super(canvas);
    }

    public Plot(List points) {
        super(points);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawLine(points.get(0).x, points.get(0).y, points.get(0).x, points.get(0).y);
    }
    //Draw the single point
    @Override
    public void mousePressedAction(MouseEvent e){
        addPoint(e.getX(),e.getY());
        canvas.addCommand(getCommand(canvas));
        canvas.clean();
        canvas.readCommands();
        points.clear();
    }

    //Plot is a single click shape so no release action
    @Override
    public void mouseReleasedAction(MouseEvent e) {
    }


}
