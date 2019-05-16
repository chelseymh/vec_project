package Shapes;

import java.awt.*;

public class Polygon extends Shape {
    private int[] xPoints, yPoints;
    private int nPoints;

    public Polygon(int[] xPoints, int[] yPoints, int nPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.nPoints = nPoints;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawPolygon(xPoints, yPoints, nPoints);
    }
}
