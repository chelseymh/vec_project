package paint_gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotUndoException;
import Shapes.*;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static Object toggledButton = null;
    private Box horizontalBoxPanel = Box.createHorizontalBox();
    private Box verticalBoxPanel = Box.createVerticalBox();
    Canvas canvas;
    private String tool = "PEN";
    private boolean fill = false;

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

        // Add action listeners
        fileOpen.addActionListener(e -> {
            try {
                openFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        fileSave.addActionListener(actionEvent -> {
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add menu items
        file.add(fileOpen);
        file.add(fileSave);

        // Edit the panels
        verticalPanel.setPreferredSize(new Dimension(100, 500));
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
        setPreferredSize(new Dimension(400, 400));
        setLocation(new Point(200, 200));
        setJMenuBar(fileMenu);
        getContentPane().setBackground(Color.white);
        pack();
        setVisible(true);

        addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                System.out.println("Window has been resized");
                System.out.println(canvas.getHeight());
                System.out.println(canvas.getWidth());

                if (ev.getComponent().getWidth()> ev.getComponent().getHeight()){
                    canvas.setBounds(150,50,ev.getComponent().getHeight(),ev.getComponent().getHeight());
                    canvas.imageSizex=ev.getComponent().getHeight();
                    canvas.imageSizey=ev.getComponent().getHeight();
                } else {
                    canvas.setBounds(150,50,ev.getComponent().getWidth(),ev.getComponent().getWidth());
                    canvas.imageSizex=ev.getComponent().getWidth();
                    canvas.imageSizey=ev.getComponent().getWidth();
                }

                if (canvas.image!=null) {
                    canvas.clean();
                    canvas.readCommands();
                    canvas.repaint();
                }
            }
        });
    }

    public void createButtonTools() {
        JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn, undoBtn;
        JToggleButton fillBtn;
        JButton black, blue, red, green, otherColor;

        plotBtn = createButton("Plot");
        ellipseBtn = createButton("Ellipse");
        lineBtn = createButton("Line");
        polygonBtn = createButton("Polygon");
        rectangleBtn = createButton("Rectangle");

        undoBtn = createButton("Undo");

        fillBtn = makeFillButton();

        black = createButton("Black");
        blue = createButton("Blue");
        red = createButton("Red");
        green = createButton("Green");
        otherColor = createButton("Other");

        horizontalBoxPanel.add(plotBtn); horizontalBoxPanel.add(rectangleBtn);
        horizontalBoxPanel.add(lineBtn); horizontalBoxPanel.add(ellipseBtn);
        horizontalBoxPanel.add(polygonBtn);

        verticalBoxPanel.add(undoBtn);
        verticalBoxPanel.add(new JLabel("1. Fill on/off:"));
        verticalBoxPanel.add(fillBtn);
        verticalBoxPanel.add(new JLabel("2. Choose color:"));
        verticalBoxPanel.add(black);
        verticalBoxPanel.add(blue);
        verticalBoxPanel.add(red);
        verticalBoxPanel.add(green);
        verticalBoxPanel.add(otherColor);
    }

    public JButton createButton(String name) {
        JButton tempBtn = new JButton(name);
        tempBtn.addActionListener(actionEvent -> {
            switch (name) {
                case "Undo":
                    canvas.Undo();
                    break;
                case "Black":
                    canvas.addCommand(tool + " #000000");
                    break;
                case "Blue":
                    canvas.addCommand(tool + " #0000FF");
                    break;
                case "Red":
                    canvas.addCommand(tool + " #FF0000");
                    break;
                case "Green":
                    canvas.addCommand(tool + " #00FF00");
                    break;
                case "Other":
                    Color color = JColorChooser.showDialog(this, "Color chooser",null);
                    String hex = "#" + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue());
                    canvas.addCommand(tool + " " + hex);
                    break;
                case "Polygon":
                    toggledButton = name;
                    Shapes.Polygon polygon = new Shapes.Polygon();
                    polygon.create(canvas);
                    System.out.println("Poly selected");
                    break;
                case "Rectangle":
                    toggledButton = name;
                    Shapes.Rectangle rect = new Shapes.Rectangle();
                    rect.create(canvas);
                    System.out.println("Rect selected");
                    break;
                case "Ellipse":
                    toggledButton = name;
                    Shapes.Ellipse ellipse = new Shapes.Ellipse();
                    ellipse.create(canvas);
                    System.out.println("Ellip selected");
                    break;
                case "Line":
                    toggledButton = name;
                    Shapes.Line line = new Shapes.Line();
                    line.create(canvas);
                    System.out.println("Line selected");
                    break;
                case "Plot":
                    toggledButton = name;
                    Shapes.Plot plot = new Shapes.Plot();
                    plot.create(canvas);
                    System.out.println("Plot selected");
                    break;
                default:
                    //toggledButton = name;
                    //System.out.println("Selected button: " + toggledButton);
            }
            //toggledButton = name;

        });
        return tempBtn;
    }

    private JToggleButton makeFillButton() {
        JToggleButton button = new JToggleButton("Fill");
        button.addActionListener(actionEvent -> {
            fill = !fill;
            if (fill) tool = "FILL";
            else {
                tool = "PEN";
                canvas.addCommand("FILL OFF");
            }
        });
        return button;
    }

    private void openFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
            canvas.getCommands().clear();
            for (String lineFile = reader.readLine(); lineFile != null; lineFile = reader.readLine())
            {
                canvas.addCommand(lineFile);
            }
            canvas.clean();
            canvas.readCommands();
        }
    }

    private void saveFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            // Check the type of the file
            if (!filePath.endsWith(".vec")) fileToSave = new File(filePath.concat(".vec"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
            for (String command : canvas.getCommands()) {
                writer.write(command);
                writer.newLine();
            }
            writer.close();
        }
    }
}
