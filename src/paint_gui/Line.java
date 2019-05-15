package paint_gui;

import java.awt.*;

public class Line {
    private int startX, startY, endX, endY;
    public Line(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
    public void draw(Graphics g) {
        g.drawLine(startX, startY, endX, endY);
    }
}
