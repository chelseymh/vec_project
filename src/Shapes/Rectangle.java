package Shapes;

import java.awt.*;

public class Rectangle extends Shape {
    private int x, y, height, width;

    public void Rectangle(int x1, int y1, int x2, int y2) {
        //Rectangle works by starting xy point followed by desired width
        // and height, we get this by getting the difference of our start
        // and end coords
        // Revert if rectangle is drawn backwards
        this.x = x1;
        this.y = y1;
        this.height = x2;
        this.width = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        if (x != 0 || height != 0) {
            boolean revertX = x < height;
            boolean revertY = y < width;
            g.drawRect(revertX ? x : height, revertY ? y : width, revertX ? Math.abs(height - x) : Math.abs(x - height),
                    revertY ? Math.abs(width - y) : Math.abs(y - width));
        }
    }

    public String getCommand(){
        String command= String.format("RECTANGLE %1$d %2$d %3$d %4$d", x, y, height, width);
        return command;
    }
}
