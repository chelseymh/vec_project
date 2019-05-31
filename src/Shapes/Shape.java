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
    private List<Point> points= new ArrayList<Point>();
    private boolean done;
    private String className=this.getClass().getSimpleName();


    public Shape(){}

    public Shape(List points) {
        System.out.println("points = [" + points + "]");
        this.points.addAll(points);
    }

    public Shape(Canvas canvas) {
        this.canvas = canvas;
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
                    canvas.addCommand(getCommand(canvas));
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
                drawPreview(e, canvas);
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

    public String getCommand(Canvas canvas){
        String command=className.toUpperCase();
        for (Point point : this.points){
            command+= String.format(" %1$.2f %2$.2f", (float)point.getX()/canvas.getHeight(), (float)point.getY()/canvas.getWidth());
        }
        return command;
    }

    public void drawPreview(MouseEvent e, Canvas canvas){
        Point previewPoint = new Point(e.getX(), e.getY());
        //temporarily add the preview point
        points.add(previewPoint);
        canvas.clean();
        canvas.readCommands();
        draw(canvas.getTheInk());
        canvas.repaint();
        //remove last element added which is the preview point
        points.remove(points.size()-1);
    }

    public List<Point> getPoints(){
        return points;
    }
    public abstract void draw(Graphics2D g);
    public abstract void fill(Graphics2D g);
}


