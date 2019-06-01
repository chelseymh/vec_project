package paint_gui;

import Shapes.*;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
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
                    List points= new ArrayList<Point>();
                    String className=input[0].toLowerCase();
                    //make first letter caps to match class names
                    String classNameCapped = className.substring(0, 1).toUpperCase() + className.substring(1);

                    //read points to a list for transportation to the constructor
                    int i = 1;
                    while (i < input.length - 1) {
                        Point point = new Point();
                        //Convert back to unscaled int
                        point.setLocation(Float.parseFloat(input[i])*getHeight(), Float.parseFloat(input[i + 1])*getWidth());
                        points.add(point);
                        i = i + 2;
                    }
                    try {
                        Class shapeClass = Class.forName("Shapes."+ classNameCapped);
                        System.out.println("\nClass " +classNameCapped +" found");
                        Shapes.Shape shape =(Shapes.Shape)shapeClass.getConstructor(List.class).newInstance(points);
                        shape.draw(theInk);
                        if (fill && shape instanceof FillingShape) ((FillingShape) shape).fill(fillInk);
                    } catch (ClassNotFoundException e){
                        System.out.println("Class not found");
                    } catch (NoSuchMethodException e){
                        System.out.println("No fucking method");
                    } catch (InstantiationException e) {
                        System.out.println("Fucking instantiation exception");
                    } catch (IllegalAccessException e) {
                        System.out.println("Fucking illegal access exception");
                    } catch (InvocationTargetException e) {
                        System.out.println("Fucking ITE exception");
                    }
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

