package paint_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JComponent {
    guiClass gui;
    Dimension minSize = new Dimension(200, 200);
    private int x1, y1, x2, y2;
    Graphics2D theInk;
    Image image;

    public Canvas(Color color) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color);
        setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (gui.toggledButton != null && gui.toggledButton != "Plot") {
                    x1 = e.getX();
                    y1 = e.getY();
                    System.out.println("Master: Start co-ords are: " + x1 + " and " + y1);
                }
            }
        });
    }
    //Java Swing is a black box of graphics and will call this
    //as needed to paint components on the canvas
    //bit dodgy may need to look at again in the future
    //Takes a graphics component to draw on but since we don't call
    //it ourselves, Swing takes care of it
    public void paintComponent(Graphics graphics){
        //if there's no image already
        if(image == null){
            //taken from window dimensions in guiClass
            image = createImage(600, 600);
            theInk = (Graphics2D)image.getGraphics();
            theInk.setPaint(Color.DARK_GRAY);
            theInk.setStroke(new BasicStroke(4));
            image.flush();
        }
        graphics.drawImage(image, 0, 0, null);
    }

    public void Plot(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                if (theInk != null && gui.toggledButton.equals("Plot"))
                    System.out.println("Plot: Start co-ords are: " + x1 + " and " + y1);
                theInk.drawLine(x1, y1, x1, y1);
                repaint();
            }
        });
    }

    public void Ellipse(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Drag in motion");
                x2 = e.getX();
                y2 = e.getY();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e){
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();
                if (theInk != null && gui.toggledButton.equals("Ellipse"))
                    System.out.println("End co-ords are: " + x2 + " and " + y2);
                //ellipse function takes xy coords of start followed by width and height,
                // we get this by getting the difference of our start and end coords
                //theInk.drawOval(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));

                // Revert if ellipse is reverted
                if (x1 !=0 || x2 !=0) {
                    boolean revertX = x1 < x2;
                    boolean revertY = y1 < y2;
                    theInk.drawOval(revertX ? x1 : x2, revertY ? y1 : y2, revertX ? Math.abs(x2-x1) : Math.abs(x1-x2),
                            revertY ? Math.abs(y2-y1) : Math.abs(y1-y2));
                }
                repaint();
            }
        });
    }

    public void Line(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();
                if (theInk != null && gui.toggledButton.equals("Line")) {
                    System.out.println("End co-ords are: " + x2 + " and " + y2);
                    theInk.drawLine(x1, y1, x2, y2);
                }
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                System.out.println("Drag in motion");
                theInk.drawLine(x1, y1, x2, y2);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        });
    }

    public void Polygon(){
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e){
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();
                if (theInk != null)
                    System.out.println("End co-ords are: " + x2 + " and " + y2);
                theInk.drawLine(x1, y1, x2, y2);
                repaint();
            }
        });

    }

    public void Rectangle(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
            }
            @Override
            public void mouseMoved(MouseEvent m) {
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e) {
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();
                if (theInk != null && gui.toggledButton.equals("Rectangle")) {
                    System.out.println("End co-ords are: " + x2 + " and " + y2);
                    //Rectangle works by starting xy point followed by desired width
                    // and height, we get this by getting the difference of our start
                    // and end coords

                    // Revert if rectangle is drawn backwards
                    if (x1 != 0 || x2 != 0) {
                        boolean revertX = x1 < x2;
                        boolean revertY = y1 < y2;
                        theInk.drawRect(revertX ? x1 : x2, revertY ? y1 : y2, revertX ? Math.abs(x2 - x1) : Math.abs(x1 - x2),
                                revertY ? Math.abs(y2 - y1) : Math.abs(y1 - y2));
                    }
                    repaint();
                }
            }
        });

    }


    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }
}
