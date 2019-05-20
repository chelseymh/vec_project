package paint_gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.undo.CannotUndoException;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static Object toggledButton = null;
    private Box horizontalBoxPanel = Box.createHorizontalBox();
    private Box verticalBoxPanel = Box.createVerticalBox();
<<<<<<< HEAD
    Canvas canvas;
=======
    private String tool = "PEN";
>>>>>>> Implemented colours for quick selection in a very simplistic way

    /**
     * Create the GUI and display it.
     */
    public void createGUI() {
        JMenuBar fileMenu;
        JMenu file;
        JMenuItem fileOpen, fileSave;

        // Build two tool bars
        JPanel verticalPanel = new JPanel();
        JPanel horizontalPanel = new JPanel();

        // Create and set up window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build top menu and first file dropdown
        fileMenu = new JMenuBar();
        fileMenu.setOpaque(true);
        fileMenu.setBackground(Color.cyan);
        fileMenu.setPreferredSize(new Dimension(200, 20));
        file = new JMenu("File");
        // Add listener here
        fileMenu.add(file);

        // Build sub menu for fileOpen and fileSave
        fileOpen = new JMenuItem("Open file");
        fileSave = new JMenuItem("Save");
        // Add listener here
        file.add(fileOpen);
        file.add(fileSave);

        // Edit the panels
<<<<<<< HEAD
        verticalPanel.setPreferredSize(new Dimension(70, 500));
=======
        verticalPanel.setPreferredSize(new Dimension(100, 500));
>>>>>>> Implemented colours for quick selection in a very simplistic way
        horizontalPanel.setPreferredSize(new Dimension(500, 50));

        // Instantiate the canvas
        canvas = new Canvas(Color.white);

        // Call the toolboxes to build
        createButtonTools(); // horizontal one
        verticalPanel.add(verticalBoxPanel);
        horizontalPanel.add(horizontalBoxPanel);

        // Add panels to control pane
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(horizontalPanel, BorderLayout.PAGE_START);
        getContentPane().add(verticalPanel, BorderLayout.WEST);

        // Display the window
        setPreferredSize(new Dimension(600, 600));
        setLocation(new Point(200, 200));
        setJMenuBar(fileMenu);
        getContentPane().setBackground(Color.white);
        pack();
        setVisible(true);
    }

    public void createButtonTools() {
        JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn, undoBtn, penBtn;
        JButton black, blue, red, green;

        plotBtn = createButton("Plot");
        ellipseBtn = createButton("Ellipse");
        lineBtn = createButton("Line");
        polygonBtn = createButton("Polygon");
        rectangleBtn = createButton("Rectangle");

        undoBtn = createButton("Undo");
        undoBtn.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  canvas.Undo();
              }
        });

        penBtn = createButton("Pen");

        black = createButton("Black");
        blue = createButton("Blue");
        red = createButton("Red");
        green = createButton("Green");

        horizontalBoxPanel.add(plotBtn); horizontalBoxPanel.add(rectangleBtn);
        horizontalBoxPanel.add(lineBtn); horizontalBoxPanel.add(ellipseBtn);
        horizontalBoxPanel.add(polygonBtn);

        verticalBoxPanel.add(undoBtn);
        verticalBoxPanel.add(new JLabel("1. Choose tool:"));
        verticalBoxPanel.add(penBtn);
        verticalBoxPanel.add(new JLabel("2. Choose color:"));
        verticalBoxPanel.add(black);
        verticalBoxPanel.add(blue);
        verticalBoxPanel.add(red);
        verticalBoxPanel.add(green);
    }

    public JButton createButton(String name) {
        JButton tempBtn = new JButton(name);
        tempBtn.addActionListener(actionEvent -> {
            switch (name) {
                case "Pen":
                    tool = "PEN";
                    break;
                case "Black":
                    Canvas.commands.add(tool + " #000000");
                    break;
                case "Blue":
                    Canvas.commands.add(tool + " #0000FF");
                    break;
                case "Red":
                    Canvas.commands.add(tool + " #FF0000");
                    break;
                case "Green":
                    Canvas.commands.add(tool + " #00FF00");
                    break;
                default:
                    toggledButton = name;
                    System.out.println("Selected button: " + toggledButton);
            }
        });
        return tempBtn;
    }
}
