package Shapes;

import java.awt.*;

public class Polygon extends Shape implements FillingShape {
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

    public String getCommand(){
        //place holder
        String command= String.format("POLYGON %1$d %2$d %3$d %4$d", xPoints[0], yPoints[0], xPoints[1], yPoints[0]);
        return command;
    }

    @Override
    public void fill(Graphics2D g) {
        //TODO
    }
}
