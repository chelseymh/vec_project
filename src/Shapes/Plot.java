package Shapes;

import javax.swing.*;
import java.awt.*;

public class Plot extends Shape {
    private int x1, y1, x2, y2;

    public void Plot(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    @Override
    public void draw(Graphics2D g) {
        g.drawLine(x1, y1, x2, y2);
    }
}
