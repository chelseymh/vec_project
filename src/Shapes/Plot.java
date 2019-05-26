package Shapes;

import javax.swing.*;
import java.awt.*;
import paint_gui.Canvas;

//mouse handlers
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Plot extends Shape {
    private int x1, y1;
    private paint_gui.Canvas canvas;
    private Graphics2D theInk;

    public void Plot(){

    }

    public void addPoints(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public void create(Canvas canvas) {
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

                System.out.println(getCommand());
                canvas.addCommand(getCommand());

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
