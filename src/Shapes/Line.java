package Shapes;

import java.awt.*;

public class Line extends Shape {
    private int x1, y1, x2, y2;

    public void Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public void draw(Graphics2D g) {
        g.drawLine(x1, y1, x2, y2);
    }

    public String getCommand(){
        String command= String.format("LINE %1$d %2$d %3$d %4$d", x1, y1, x2, y2);
        return command;
    }
}
