package Shapes;

import java.awt.*;
import paint_gui.Canvas;
import paint_gui.guiClass;

//mouse handlers
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Rectangle extends Shape implements FillingShape {
    private int x, y, height, width;
    private paint_gui.Canvas canvas;
    private Graphics2D theInk;

    public void Rectangle() {
;
    }

    public void addPoints(int x1, int y1, int x2, int y2){
        //Rectangle works by starting xy point followed by desired width
        // and height, we get this by getting the difference of our start
        // and end coords
        // Revert if rectangle is drawn backwards
        this.x = x1;
        this.y = y1;
        this.height = x2;
        this.width = y2;
        //canvas to paint shape on


    }

    public void create(Canvas canvas) {
        this.canvas = canvas;
        this.theInk = canvas.getTheInk();
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
                if (guiClass.toggledButton=="Rectangle") {
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
                if (guiClass.toggledButton=="Rectangle") {

                    height = e.getX();
                    width = e.getY();
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

    @Override
    public void fill(Graphics2D g) {
        if (x != 0 || height != 0) {
            boolean revertX = x < height;
            boolean revertY = y < width;
            g.fillRect(revertX ? x : height, revertY ? y : width, revertX ? Math.abs(height - x) : Math.abs(x - height),
                    revertY ? Math.abs(width - y) : Math.abs(y - width));
        }
    }
}
