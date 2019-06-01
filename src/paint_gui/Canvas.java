package paint_gui;

import Shapes.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends <code>javax.swing.JComponent</code> and handles the drawing
 *  storing, and displaying of shapes. Key components include <code>theInk</code>, a Graphics2D
 *  instance that handles the paint settings and <code>image</code>, an Image instance on which
 *  Shapes are displayed on. <code>commands</code> a List containing the vec file commands for
 *  the session.
 */
public class Canvas extends JComponent {
    private Graphics2D theInk;
    private Image image;
    private List<String> commands = new ArrayList<String>();
    private boolean fill = false;

    /**
     * Instantiates a new canvas
     */
    public Canvas() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        setOpaque(true);
    }

    /**
     * Used to resize the image when the window has been
     * resized. Gets parameters from newly resized canvas
     * and creates new image of the same size
     * and creates a new instance of the Graphics2D tool
     * to go with the new image
     */
    public void refreshCanvas(){
        image = createImage(getWidth(), getHeight());
        theInk = (Graphics2D)image.getGraphics();
        theInk.setPaint(Color.white);
        theInk.fillRect(0, 0, getWidth(), getHeight());
        theInk.setPaint(Color.DARK_GRAY);
        theInk.setStroke(new BasicStroke(4));
    }

    /**
     * Called indirectly by Swing, used by the repaint
     * function
     * Creates a new image if there is none already
     * then draws requested images
     * * @param g Takes a Graphics instance passed
     * automatically by Swing
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //if there's no image already
        //create the blank image
        if(image == null){
           refreshCanvas();
        }
        g.drawImage(image, 0, 0, null);
    }

    /**
     * Wipes the image clean, sets the Graphics2D tool back
     * to original color
     */
    public void clean(){
        theInk.setPaint(Color.white);
        theInk.fillRect(0, 0, getHeight(), getWidth());
        theInk.setPaint(Color.DARK_GRAY);
        theInk.setStroke(new BasicStroke(4));
        repaint();
    }

    /**
     * Reads the stored string commands in the List <code>commands</code>
     * By default, creates the class of a shape command stored in
     * the <code>commands</code> with the points also stored in the
     * string. As the points are stored as floats in the VEC format, they
     * are scaled according to canvas size and converted back to int format.
     * Finally the shape is drawn using the draw method associated with
     * its class. If not a shape command, sets pen or fill options.
     */
    public void readCommands(){
        Graphics2D fillInk = (Graphics2D) image.getGraphics();

        //Reset theInk and fill to their default values
        theInk.setPaint(Color.DARK_GRAY);
        if (fill) fill = false;
        for (String lineFile : commands)
        {
            String[] input = lineFile.split("\\s");
            switch (input[0].toLowerCase()) {
                //Reads a PEN color VEC command
                case "pen":
                    System.out.println("pen");
                    theInk.setPaint(Color.decode(input[1]));
                    break;
                    //Reads a FILL OFF VEC command
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
                        Shapes.Shape shape =(Shapes.Shape)shapeClass.getConstructor(List.class).newInstance(points);
                        shape.draw(theInk);
                        if (fill && shape instanceof FillingShape) ((FillingShape) shape).fill(fillInk);
                    } catch (Exception e){
                        System.out.println("Exception caught in the generic constructor");
                    }
                    break;
            }
        }
        repaint();
    }

    /**
     * Called whenever another class needs to directly modify the <code>commands</code>
     * list
     * @return Gets the list of string commands stored in <code>commands</code>
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * Used to add new commands to the <code>commands</code> list. Used by shape constructors
     * and <code>FileHandler</code>. Also used in <code>GUI</code> to add color and fill
     * commands when the respective button handlers have been triggered.
     * @param command the VEC format command to be added to the <code>commands</code> list
     *                of strings
     */
    public void addCommand(String command) {
        commands.add(command);
    }

    /**
     * Used when a class needs to directly call the <code>theInk</code>
     * associated with the canvas
     * @return Returns the Graphics2D component attached to the canvas
     */
    public Graphics2D getTheInk() {
        return theInk;
    }

    /**
     * Used when a class needs to directly call the <code>image</code>
     * associated with the canvas
     * @return Returns the image attached to the canvas
     */
    public Image getImage() {
        return image;
    }
}

