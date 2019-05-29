package paint_gui;

import Shapes.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Canvas extends JComponent {
    //private Dimension minSize = new Dimension(300, 300);
    private Graphics2D theInk;
    private Image image;
    private List<String> commands = new ArrayList<String>();
    private boolean fill = false;

    public Canvas(Color color) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color.white);
        setOpaque(true);
    }

    public void refreshCanvas(){
        image = createImage(getWidth(), getHeight());
        theInk = (Graphics2D)image.getGraphics();
        theInk.setPaint(Color.white);
        theInk.fillRect(0, 0, getWidth(), getHeight());
        theInk.setPaint(Color.DARK_GRAY);
        theInk.setStroke(new BasicStroke(4));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //if there's no image already
        //create the blank image
        if(image == null){
           refreshCanvas();
        }
        g.drawImage(image, 0, 0, null);
    }

    public void clean(){
        theInk.setPaint(Color.white);
        theInk.fillRect(0, 0, getHeight(), getWidth());
        theInk.setPaint(Color.DARK_GRAY);
        theInk.setStroke(new BasicStroke(4));
        repaint();
    }

    public void readCommands(){
        Graphics2D fillInk = (Graphics2D) image.getGraphics();

        //Reset theInk and fill to their default values
        theInk.setPaint(Color.DARK_GRAY);
        if (fill) fill = false;
        for (String lineFile : commands)
        {
            String[] input = lineFile.split("\\s");
            switch (input[0].toLowerCase()) {
                case "line":
                    //System.out.println("line");
                    Shapes.Line line = new Shapes.Line((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()), (int)(Float.parseFloat((input[3]))*getHeight()), (int)(Float.parseFloat(input[4])*getWidth()));
                    line.draw(theInk);
                    break;
                case "plot":
                    //System.out.println("plot");
                    Plot plot = new Plot((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()));
                    plot.draw(theInk);

                    break;
                case "rectangle":
                    //System.out.println("rectangle");
                    Shapes.Rectangle rect = new Shapes.Rectangle((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()), (int)(Float.parseFloat((input[3]))*getHeight()), (int)(Float.parseFloat(input[4])*getWidth()));
                    rect.draw(theInk);
                    if (fill) rect.fill(fillInk);
                    break;
                case "ellipse":
                    //System.out.println("ellipse");
                    Ellipse ellipse = new Ellipse((int)(Float.parseFloat(input[1])*getHeight()), (int)(Float.parseFloat((input[2]))*getWidth()), (int)(Float.parseFloat((input[3]))*getHeight()), (int)(Float.parseFloat(input[4])*getWidth()));
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
                    break;
                case "fill":
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
        repaint();
    }

    public List<String> getCommands() {
        return commands;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public Graphics2D getTheInk() {
        return theInk;
    }

    public Image getImage() {
        return image;
    }
}

