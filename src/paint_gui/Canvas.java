package paint_gui;

import Shapes.*;
import Shapes.Rectangle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Canvas extends JComponent {
    private guiClass gui;
    private Dimension minSize = new Dimension(200, 200);
    private static final String NEWLINE = System.getProperty("line.separator");
    private int x1, y1, x2, y2;
    private Graphics2D theInk;
    private Image image;

    public Canvas(Color color) {
        gui.toggledButton = "Plot"; // By default, the Plot tool is toggled
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color.white);
        setOpaque(true);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                printEvent("Mouse clicked", e);
                //repaint();
                if (theInk != null && gui.toggledButton.equals("Plot")) {
                    Plot plot = new Plot();
                    plot.Plot(x1, y1, x2, y2);
                    System.out.println(plot.getCommand());
                    plot.draw(theInk);
                    repaint();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                printEvent("Mouse released", e);
                repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                printEvent("Mouse dragged", e);
                repaint();
            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        });
    }
    //Java Swing is a black box of graphics and will call this
    //as needed to paint components on the canvas
    //bit dodgy may need to look at again in the future
    //Takes a graphics component to draw on but since we don't call
    //it ourselves, Swing takes care of it
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //if there's no image already
        if(image == null){
            //taken from window dimensions in guiClass
            image = createImage(600, 600);
            theInk = (Graphics2D)image.getGraphics();
            theInk.setPaint(Color.DARK_GRAY);
            theInk.setStroke(new BasicStroke(4));
            image.flush();
        }
        g.drawImage(image, 0, 0, null);
        if (theInk != null && gui.toggledButton.equals("Line")) {
            Line line = new Line();
            line.Line(x1, y1, x2, y2);
            System.out.println(line.getCommand());
            line.draw(theInk);
        } else if (theInk != null && gui.toggledButton.equals("Rectangle")) {
            Rectangle rect = new Rectangle();
            rect.Rectangle(x1, y1, x2, y2);
            System.out.println(rect.getCommand());
            rect.draw(theInk);
        } else if (theInk != null && gui.toggledButton.equals("Ellipse")) {
            Ellipse ellipse = new Ellipse();
            ellipse.Ellipse(x1, y1, x2, y2);
            System.out.println(ellipse.getCommand());
            ellipse.draw(theInk);
        } else if (theInk != null && gui.toggledButton.equals("Polygon")) {
            // Insert Polygon code
            theInk.drawLine(x1, y1, x2, y2);
        }
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }

    void printEvent(String event, MouseEvent e) {
        System.out.println(event + " (" + e.getX() + ", "
                + e.getY() + ") " + "detected on "
                + e.getComponent().getClass().getName()
                + NEWLINE);
    }
}
