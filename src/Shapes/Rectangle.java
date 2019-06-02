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
            //draw with reverted points if needed
            drawSanitizer();
            g.drawRect(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
    }

    public void drawSanitizer(){
        //if points are valid
        if (points.get(0).x != 0 || points.get(1).x != 0) {
            //Check if x needs to be reverted
            //x needs to be reverted if the first x is greater
            //than the second
            boolean revertX = points.get(0).x > points.get(1).x;
            //Check if y needs to be reverted
            //y axis increases with down direction so if first y
            //is greater than second y revert
            boolean revertY = points.get(0).y > points.get(1).y;
            //set points with reverted points if needed
            //if x needs to be reverted, swap the first and second x points
            //if not, keep them the same
            int x1= revertX ?  points.get(1).x: points.get(0).x;
            int y1= revertY ? points.get(1).y : points.get(0).y;
            int x2 = revertX ?  points.get(0).x - points.get(1).x: points.get(1).x;
            int y2= revertY ? points.get(0).y - points.get(1).y : points.get(1).y;
            //update points
            points.clear();
            addPoint(x1, y1);
            addPoint(x2,y2);

        }

    }

    @Override
    public void fill(Graphics2D g) {
        //fill with reverted points if needed
            g.fillRect(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);

    }

}
