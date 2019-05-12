package paint_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

public class Canvas extends JComponent {


    Dimension minSize = new Dimension(200, 200);
    private int x1, y1, x2, y2;
    Graphics2D theInk;
    Image image;

    public Canvas(Color color) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color);
        setOpaque(true);

        //repaint();


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
        }
        graphics.drawImage(image, 0, 0, null);
    }

    public void Plot(){
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("Start co-ords are: " + x1 + " and " + y1);

                theInk.setPaint(Color.blue);
                if (theInk != null)
                    theInk.drawLine(x1, y1, x1, y1);
                repaint();
            }
        });
    }

    public void Ellipse(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("Start co-ords are: " + x1 + " and " + y1);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e){
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();

                System.out.println("End co-ords are: " + x2 + " and " + y2);

                theInk.setPaint(Color.blue);
                if (theInk != null)
                    theInk.draw(new Ellipse2D.Double(50, 50, 250, 250));;
                repaint();
            }
        });

    }

    public void Line(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("Start co-ords are: " + x1 + " and " + y1);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e){
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();

                System.out.println("End co-ords are: " + x2 + " and " + y2);

                theInk.setPaint(Color.blue);
                if (theInk != null)
                    theInk.drawLine(x1, y1, x2, y2);
                repaint();
            }
        });

    }

    public void Polygon(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("Start co-ords are: " + x1 + " and " + y1);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e){
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();

                System.out.println("End co-ords are: " + x2 + " and " + y2);

                theInk.setPaint(Color.blue);
                if (theInk != null)
                    theInk.drawLine(x1, y1, x2, y2);
                repaint();
            }
        });

    }

    public void Rectangle(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("Start co-ords are: " + x1 + " and " + y1);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased (MouseEvent e){
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();

                System.out.println("End co-ords are: " + x2 + " and " + y2);

                theInk.setPaint(Color.blue);
                if (theInk != null)
                    //Rectangle works by starting xy point followed by desired width
                    // and height, we get this by getting the difference of our start
                    // and end coords
                    theInk.drawRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
                repaint();
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
