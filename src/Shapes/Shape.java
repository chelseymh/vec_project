package Shapes;

import paint_gui.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
    private paint_gui.Canvas canvas;
    private Graphics2D theInk;
    private List<Point> points= new ArrayList<Point>();
    private boolean done;
    private String className=this.getClass().getSimpleName();


    public Shape(){}

    public Shape(int x1, int y1, int x2, int y2) {
        Point point1 = new Point(x1, y1);
        points.add(point1);
        Point point2 = new Point(x2, y2);
        points.add(point2);
    }

    public Shape(Canvas canvas) {
        this.canvas = canvas;
        this.theInk = canvas.getTheInk();
        done=false;

        MouseListener mouseListener=new MouseListener(){
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = new Point(e.getX(), e.getY());
                points.add(point);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (paint_gui.guiClass.toggledButton.equals(className)) {
                    Point point = new Point(e.getX(), e.getY());
                    points.add(point);
                    canvas.addCommand(getCommand());
                    canvas.clean();
                    canvas.readCommands();
                    points.clear();
                    done = true;
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        canvas.addMouseListener(mouseListener);


        MouseMotionListener mouseMotionListener= new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Point previewPoint = new Point(e.getX(), e.getY());
                //temporarily add the preview point
                points.add(previewPoint);
                canvas.clean();
                canvas.readCommands();
                draw(theInk);
                canvas.repaint();
                //remove last element added which is the preview point
                points.remove(points.size()-1);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };

        canvas.addMouseMotionListener(mouseMotionListener);

        if(done){
            canvas.removeMouseListener(mouseListener);
            canvas.removeMouseMotionListener(mouseMotionListener);
        }
    }



    public String getCommand(){

        String command=className.toUpperCase();;
        for (Point point : points){
            command+= String.format(" %1$.2f %2$.2f", (float)point.x/canvas.getHeight(), (float)point.y/canvas.getWidth());
        }
        return command;
    }

    public List<Point> getPoints(){
        return points;
    }
    public abstract void draw(Graphics2D g);
    public abstract void fill(Graphics2D g);
}


