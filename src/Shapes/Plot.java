package Shapes;

import java.awt.*;

public class Plot extends Shape {
    private int x, y;

    public Plot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawLine(x, y, x, y);
    }
}
