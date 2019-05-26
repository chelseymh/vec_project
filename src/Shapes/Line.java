package Shapes;

import java.awt.*;
import paint_gui.Canvas;
import paint_gui.guiClass;

//mouse handlers
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Line extends Shape {
    private int x1, y1, x2, y2;
    private paint_gui.Canvas canvas;
    private Graphics2D theInk;

    public void Line(){
    }

    public void addPoints(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void create(Canvas canvas){
        this.canvas = canvas;
        this.theInk = canvas.getTheInk();
        canvas.addMouseListener(new MouseListener() {


            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                //System.out.println(getCommand());
                if (guiClass.toggledButton=="Line") {
                    canvas.addCommand(getCommand());
                }

                canvas.clean();
                canvas.readCommands();
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (guiClass.toggledButton=="Line") {

                    x2 = e.getX();
                    y2 = e.getY();
                    canvas.clean();
                    canvas.readCommands();
                    draw(theInk);
                    canvas.repaint();
                }


            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        });


    }
    public void draw(Graphics2D g) {
        g.drawLine(x1, y1, x2, y2);
    }

    public String getCommand(){
        String command= String.format("LINE %1$.2f %2$.2f %3$.2f %4$.2f", (float)x1/canvas.getHeight(), (float)y1/canvas.getWidth(), (float)x2/canvas.getHeight(), (float)y2/canvas.getWidth());
        return command;
    }
}
