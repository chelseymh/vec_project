package paint_gui;

import Shapes.*;
import Shapes.Rectangle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JComponent {
    private guiClass gui;
    private Dimension minSize = new Dimension(200, 200);
    private static final String NEWLINE = System.getProperty("line.separator");
    private int x1, y1, x2, y2, x3, y3;
    private Graphics2D theInk;
    private Image image;
    private List<String> commands = new ArrayList<String>();

    public Canvas(Color color) {
        gui.toggledButton = "Plot"; // By default, the Plot tool is toggled
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color.white);
        setOpaque(true);


        addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                printEvent("Mouse pressed", e);

            }

            @Override
            public void mouseClicked(MouseEvent e) {
//                x1 = e.getX();
//                y1 = e.getY();
//                printEvent("Mouse clicked", e);
                //repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //x2 = e.getX();
                //y2 = e.getY();
                printEvent("Mouse released", e);

                if (theInk != null && gui.toggledButton.equals("Rectangle")) {
                    Rectangle rect = new Rectangle();
                    rect.Rectangle(x1, y1, x2, y2);
                    System.out.println(rect.getCommand());
                    commands.add(rect.getCommand());
                    //rect.draw(theInk);
                }

                else if (theInk != null && gui.toggledButton.equals("Line")) {
                    Line line = new Line();
                    line.Line(x1, y1, x2, y2);
                    System.out.println(line.getCommand());
                    commands.add(line.getCommand());
                    //line.draw(theInk);
                }
                clean();
                readCommands();
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                printEvent("Mouse dragged", e);
                clean();
                if (theInk != null && gui.toggledButton.equals("Rectangle")) {
                    Rectangle rect = new Rectangle();
                    rect.Rectangle(x1, y1, x2, y2);
                    rect.draw(theInk);
                }

                if (theInk != null && gui.toggledButton.equals("Line")) {
                    Line line = new Line();
                    line.Line(x1, y1, x2, y2);
                    line.draw(theInk);
                }

                repaint();
                //clean();
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
        //create the blank image
        if(image == null){
            //taken from window dimensions in guiClass
            image = createImage(600, 600);
            theInk = (Graphics2D)image.getGraphics();
            theInk.setPaint(Color.DARK_GRAY);
            theInk.setStroke(new BasicStroke(4));
            //clean();
        }
        g.drawImage(image, 0, 0, null);

        if (theInk != null && gui.toggledButton.equals("Plot")) {
            Plot plot = new Plot();
            plot.Plot(x1, y1, x2, y2);
            System.out.println(plot.getCommand());
            commands.add(plot.getCommand());
            plot.draw(theInk);





        } else if (theInk != null && gui.toggledButton.equals("Ellipse")) {
            Ellipse ellipse = new Ellipse();
            ellipse.Ellipse(x1, y1, x2, y2);
            System.out.println(ellipse.getCommand());
            commands.add(ellipse.getCommand());
            //ellipse.draw(theInk);

        } else if (theInk != null && gui.toggledButton.equals("Polygon")) {
            // Insert Polygon code
            //theInk.drawLine(x1, y1, x2, y2);
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

    public void clean(){
        theInk.setPaint(Color.white);
        theInk.fillRect(0, 0, getSize().width, getSize().height);
        theInk.setPaint(Color.DARK_GRAY);
        theInk.setStroke(new BasicStroke(4));
        repaint();
    }

    public void readCommands(){
        System.out.println("Reading commands");
        for (String lineFile : commands)
        {
            String[] input = lineFile.split("\\s");
            int[] coordinates;
            switch (input[0].toLowerCase()) {
                case "line":
                    System.out.println("line");
                    Line line = new Line();
                    line.Line(Integer.parseInt(input[1]), Integer.parseInt(input[2]), Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                    line.draw(theInk);
                    break;
                case "plot":
                    System.out.println("plot");

                    break;
                case "rectangle":
                    System.out.println("rectangle");
                    Rectangle rect = new Rectangle();
                    rect.Rectangle(Integer.parseInt(input[1]), Integer.parseInt(input[2]), Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                    rect.draw(theInk);
                    break;
                case "ellipse":
                    System.out.println("ellipse");

                    break;
                case "polygon":
                    System.out.println("polygon");

                    break;
                case "pen":
                    System.out.println("pen");
                    break;
                case "fill":
                    System.out.println("fill");
                    break;
                default:
                    break;
            }
        }
        repaint();
    }


}
