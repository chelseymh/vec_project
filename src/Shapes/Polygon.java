package Shapes;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import paint_gui.Canvas;
import paint_gui.guiClass;

//mouse handlers
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Polygon extends Shape implements FillingShape {
    //This should be better managed dictionary or something
    private List<Point> points= new ArrayList<Point>();
    //canvas to paint shape on
    private int x1, y1;
    private paint_gui.Canvas canvas;
    private Graphics2D theInk;
    private String clickstatus="firstclick";

    public Polygon(Canvas canvas) {
        this.canvas = canvas;
        this.theInk = canvas.getTheInk();
        this.points=getPoints();
        System.out.println("poly constructor");

        canvas.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {


                if (guiClass.toggledButton=="Polygon") {

                    //Polygon is marked as done with a double click
                    if (e.getClickCount() == 2) {
                        Point point = new Point(e.getX(), e.getY());
                        points.add(point);
                        Point firstPoint = points.get(0);
                        points.add(firstPoint);
                        // System.out.println(getCommand());
                        canvas.addCommand(getCommand(canvas));

                        //redraw only the saved commands
                        canvas.readCommands();

                        points.clear();
                        clickstatus = "firstclick";
                        //The first point in a polygon line segment
                    } else if (clickstatus.equals("firstclick")) {
                        Point point = new Point(e.getX(), e.getY());
                        points.add(point);
                        clickstatus = "polygoncommenced";
                        System.out.println("firstclick");
                        System.out.println("point added");
                        //redraw only the saved commands
                        canvas.readCommands();
                        //because our polygon isn't finished it hasn't been
                        //saved to commands so we draw manually
                        if (points.size() > 1) {
                            draw(theInk);
                        }

                        //The second click in a polygon line segment
                    } else {
                        //store mouse coords into a point object
                        Point point = new Point(e.getX(), e.getY());
                        //add that point to the polygon points list
                        points.add(point);
                        System.out.println("point added");
                        draw(theInk);
                        //clear the canvas of any previews
                        canvas.clean();
                        //redraw only the saved commands
                        canvas.readCommands();
                        //because our polygon isn't finished it hasn't been
                        //saved to commands so we draw manually
                        draw(theInk);
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });


        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (theInk != null && !clickstatus.equals("firstclick") && guiClass.toggledButton=="Polygon") {
                    drawPreview(e, canvas);
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (theInk != null && !clickstatus.equals("firstclick") && guiClass.toggledButton=="Polygon") {
                    drawPreview(e, canvas);
                }
            }
        });

    }


    public Polygon(List points) {
        super(points);
        this.points.addAll(points);
    }

    @Override
    public void draw(Graphics2D g) {
        //get the first point, initialize the first point with it
        int previousx=points.get(0).x, previousy=points.get(0).y;
        for (Point point : points.subList(1,points.size())) {
            g.drawLine(previousx, previousy, point.x, point.y);
            previousx = point.x;
            previousy=point.y;

            if (point== points.get(0)) {
                break;
            }
        }
    }


    @Override
    public void fill(Graphics2D g) {
        int[] xpoints =new int[points.size()];
        int[] ypoints =new int[points.size()];
        int nPoints=0;
        for (Point point : points){
            xpoints[nPoints]=point.x;
            ypoints[nPoints]=point.y;
            nPoints++;
        }

        g.fillPolygon(xpoints, ypoints, nPoints);

    }
}
