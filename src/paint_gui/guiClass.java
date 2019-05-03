package paint_gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.*;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    JMenuBar fileMenu;
    JMenu file;
    JMenuItem fileOpen, fileSave;
    JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn;

    /**
     * Create the GUI and display it.
     */
    public void createGUI() {
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

        // Build two tool bars
        JPanel verticalPanel = new JPanel();
        JPanel horizontalPanel = new JPanel();
        verticalPanel.setPreferredSize(new Dimension(32, 50));
        horizontalPanel.setPreferredSize(new Dimension(50, 500));

        // Add buttons to the vertical panel of tools
        plotBtn = new JButton("Plot");
        // Add listener here
        lineBtn = new JButton("Line");
        // Add listener here
        ellipseBtn = new JButton("Ellipse");
        // Add listener here
        polygonBtn = new JButton("Polygon");
        // Add listener here
        rectangleBtn = new JButton("Rectangle");
        // Add listener here
        verticalPanel.add(plotBtn); verticalPanel.add(rectangleBtn);
        verticalPanel.add(lineBtn); verticalPanel.add(ellipseBtn);
        verticalPanel.add(polygonBtn);

        // Add panels to control pane
        getContentPane().add(horizontalPanel, BorderLayout.WEST);
        getContentPane().add(verticalPanel, BorderLayout.PAGE_START);

        // Display the window
        setPreferredSize(new Dimension(600, 600));
        setLocation(new Point(200, 200));
        setJMenuBar(fileMenu);
        getContentPane().setBackground(Color.white);
        pack();
        setVisible(true);
    }
}
