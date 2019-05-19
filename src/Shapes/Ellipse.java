package Shapes;

import java.awt.*;

public class Ellipse extends Shape {
    private int x, y, height, width;

    public void Ellipse(int x1, int y1, int x2, int y2) {
        x = x1;
        y = y1;
        height = x2;
        width = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        //ellipse function takes xy coords of start followed by width and height,
        // we get this by getting the difference of our start and end coords
        // Revert if ellipse is reverted
        if (x != 0 || height != 0) {
            boolean revertX = x < height;
            boolean revertY = y < width;
            g.drawOval(revertX ? x : height, revertY ? y : width, revertX ? Math.abs(height - x) : Math.abs(x - height),
                    revertY ? Math.abs(width - y) : Math.abs(y - width));
        }
    }

    public String getCommand(){
        String command= String.format("ELLIPSE %1$d %2$d %3$d %4$d", x, y, height, width);
        return command;
    }
}
