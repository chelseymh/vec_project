package Shapes;

import java.awt.*;

public class Rectangle extends Shape {
    private int x, y, height, width;

    public Rectangle(int x1, int y1, int x2, int y2) {
        x = x1;
        y = y1;
        height = y2-y1;
        width = x2-x1;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawRect(x, y, width, height);
    }
}
