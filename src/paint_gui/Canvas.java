package paint_gui;

import Shapes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;


public class Canvas extends JComponent {
    private guiClass gui;
    private Dimension minSize = new Dimension(300, 300);
    private static final String NEWLINE = System.getProperty("line.separator");
    private Graphics2D theInk;
    private Image image;
    private List<String> commands = new ArrayList<String>();
    private boolean fill = false;

    public Canvas(Color color) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color.white);
        setOpaque(true);
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
            image = createImage(getSize().width, getSize().height);
            theInk = (Graphics2D)image.getGraphics();
            theInk.setPaint(Color.white);
            theInk.fillRect(0, 0, getSize().width, getSize().height);
            theInk.setPaint(Color.DARK_GRAY);
            theInk.setStroke(new BasicStroke(4));
            //clean();
        }
        g.drawImage(image, 0, 0, null);

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
        Graphics2D fillInk = (Graphics2D) image.getGraphics();

        //System.out.println("Reading commands");
        for (String lineFile : commands)
        {
            String[] input = lineFile.split("\\s");
            switch (input[0].toLowerCase()) {
                case "line":
                    //System.out.println("line");
                    Line line = new Line();
                    line.addPoints((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()), (int)(Float.parseFloat((input[3]))*getHeight()), (int)(Float.parseFloat(input[4])*getWidth()));
                    line.draw(theInk);
                    break;
                case "plot":
                    //System.out.println("plot");
                    Plot plot = new Plot();
                    plot.addPoints((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()));
                    plot.draw(theInk);

                    break;
                case "rectangle":
                    //System.out.println("rectangle");
                    Shapes.Rectangle rect = new Shapes.Rectangle();
                    rect.addPoints((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()), (int)(Float.parseFloat((input[3]))*getHeight()), (int)(Float.parseFloat(input[4])*getWidth()));
                    rect.draw(theInk);
                    if (fill) rect.fill(fillInk);
                    break;
                case "ellipse":
                    //System.out.println("ellipse");
                    Ellipse ellipse = new Ellipse();
                    ellipse.addPoints((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()), (int)(Float.parseFloat((input[3]))*getHeight()), (int)(Float.parseFloat(input[4])*getWidth()));
                    ellipse.draw(theInk);
                    if (fill) ellipse.fill(fillInk);
                    break;
                case "polygon":
                    //System.out.println("polygon");
                    Shapes.Polygon polygon = new Shapes.Polygon();
                    int i = 1;
                    while (i < input.length - 1) {
                        Point point = new Point();
                        point.setLocation(Float.parseFloat(input[i])*getHeight(), Float.parseFloat(input[i + 1])*getWidth());
                        polygon.addPoints(point);
                        i = i + 2;
                    }
                    polygon.draw(theInk);
                    if (fill) polygon.fill(fillInk);

                    break;
                case "pen":
                    System.out.println("pen");
                    theInk.setPaint(Color.decode(input[1]));
                    fill = false;
                    break;
                case "fill":
                    System.out.println("fill");
                    if (input[1].equals("OFF")) {
                        fill = false;
                    } else {
                        fillInk.setPaint(Color.decode(input[1]));
                        fill = true;
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println("readcommands: "+commands);
        repaint();
    }

    public List<String> getCommands() {
        return commands;
    }


    public void Undo() {
        int size = commands.size() -1;
        if (!commands.isEmpty()) {
            commands.remove(size);
            System.out.println("Undo done");
            System.out.println(commands);
        }
        clean();
        readCommands();
        repaint();
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public Graphics2D getTheInk() {
        return theInk;
    }

}

