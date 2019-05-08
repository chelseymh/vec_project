package paint_gui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static String toggledButton = null;
    private Box horizontalBoxPanel = Box.createHorizontalBox();
    private Box verticalBoxPanel = Box.createVerticalBox();

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
        fileOpen.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedReader in = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
                    for (String line = in.readLine(); line != null; line = in.readLine())
                    {
                        String[] input = line.split("\\s");
                        switch (input[0].toLowerCase()) {
                            case "line":
                                System.out.println("line");
                                break;
                            case "plot":
                                System.out.println("plot");
                                break;
                            case "rectangle":
                                System.out.println("rectangle");
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

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        fileSave = new JMenuItem("Save");
        // Add listener here
        file.add(fileOpen);
        file.add(fileSave);

        // Edit the panels
        verticalPanel.setPreferredSize(new Dimension(50, 500));
        horizontalPanel.setPreferredSize(new Dimension(500, 50));

        // Call the toolboxes to build
        createButtonTools(); // horizontal one
        verticalPanel.add(verticalBoxPanel);
        horizontalPanel.add(horizontalBoxPanel);

        // Create a canvas
        Canvas canvas;
        canvas = new Canvas(Color.white);

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
        JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn;
        // Add buttons to the vertical panel of tools
        plotBtn = createButton("Plot");
        ellipseBtn = createButton("Ellipse");
        lineBtn = createButton("Line");
        polygonBtn = createButton("Polygon");
        rectangleBtn = createButton("Rectangle");
        horizontalBoxPanel.add(plotBtn); horizontalBoxPanel.add(rectangleBtn);
        horizontalBoxPanel.add(lineBtn); horizontalBoxPanel.add(ellipseBtn);
        horizontalBoxPanel.add(polygonBtn);
    }

    public JButton createButton(String name) {
        JButton tempBtn = new JButton(name);
        tempBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggledButton = name;
                System.out.println("Selected button: " + toggledButton);
            }
        });
        return tempBtn;
    }
}
