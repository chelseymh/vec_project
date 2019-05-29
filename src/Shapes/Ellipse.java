package Shapes;

import paint_gui.Canvas;
import paint_gui.guiClass;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Ellipse extends Shape implements FillingShape {
    private int x, y, height, width;
    private paint_gui.Canvas canvas;
    private Graphics2D theInk;


    public Ellipse(int x1, int y1, int x2, int y2) {
        x = x1;
        y = y1;
        height = x2;
        width = y2;
    }

    public Ellipse(Canvas canvas) {
        this.canvas = canvas;
        this.theInk = canvas.getTheInk();
        if (guiClass.toggledButton=="Ellipse") {
            canvas.addMouseListener(new MouseListener() {


                @Override
                public void mousePressed(MouseEvent e) {
                    x = e.getX();
                    y = e.getY();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    height = e.getX();
                    width = e.getY();

                    // System.out.println(getCommand());
                    if (guiClass.toggledButton=="Ellipse") {
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
                    if (guiClass.toggledButton=="Ellipse") {

                        height = e.getX();
                        width = e.getY();
                        canvas.clean();
                        canvas.readCommands();
                        draw(theInk);
                        canvas.repaint();
                    }

                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });
            
        }
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
        String command= String.format("ELLIPSE %1$.2f %2$.2f %3$.2f %4$.2f", (float)x/canvas.getHeight(), (float)y/canvas.getWidth(), (float)height/canvas.getHeight(), (float)width/canvas.getWidth());
        return command;
    }

    @Override
    public void fill(Graphics2D g) {
        if (x != 0 || height != 0) {
            boolean revertX = x < height;
            boolean revertY = y < width;
            g.fillOval(revertX ? x : height, revertY ? y : width, revertX ? Math.abs(height - x) : Math.abs(x - height),
                    revertY ? Math.abs(width - y) : Math.abs(y - width));
        }
    }
}
