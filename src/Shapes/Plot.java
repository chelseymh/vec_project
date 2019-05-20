package Shapes;

import javax.swing.*;
import java.awt.*;

public class Plot extends Shape {
    private int x1, y1;

    public void Plot(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }
    @Override
    public void draw(Graphics2D g) {
        g.drawLine(x1, y1, x1, y1);
    }

    public String getCommand(){
        String command= String.format("PLOT %1$d %2$d", x1, y1);
        return command;
    }
}
