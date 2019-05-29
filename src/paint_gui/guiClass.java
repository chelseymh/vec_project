package paint_gui;

import FileHandlers.ExporterBMP;
import FileHandlers.FileHandler;
import Exceptions.UndoException;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.io.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static Object toggledButton = null;
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    Canvas canvas;
    private String tool = "PEN";
    private boolean fill = false;
    History history;
    private boolean undoHisOpen;
    private FileHandler fileHandler;

    /**
     * Create the GUI and display it.
     */
    public void createGUI() {
        // Instantiate the canvas
        canvas = new Canvas(Color.white);
        history = new History(canvas);
        undoHisOpen = true;

        JMenuBar fileMenu;
        JMenu file, edit;
        JMenuItem fileNew, fileOpen, fileSave, undo, undoHistory, fileExportBMP;
        edit = new JMenu("Edit");

        // Build two tool bars
        add(eastPanel);
        add(westPanel);

        // Create and set up window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Build top menu and first file dropdown
        fileMenu = new JMenuBar();
        fileMenu.setOpaque(true);
        fileMenu.setBackground(Color.white);
        fileMenu.setPreferredSize(new Dimension(200, 20));
        file = new JMenu("File");
        // Add listener here
        fileMenu.add(file);
        fileMenu.add(edit);

        //Instantiate file handlers
        fileHandler = new FileHandler(canvas);
        ExporterBMP BMPexporter = new ExporterBMP(canvas);

        // Build sub menu for fileOpen and fileSave
        fileNew = new JMenuItem("New file");
        fileOpen = new JMenuItem("Open file");
        fileSave = new JMenuItem("Save");
        fileExportBMP = new JMenuItem("Export as BMP");

        // Add action listeners
        fileNew.addActionListener(e -> {
            guiClass gui = new guiClass();
            gui.createGUI();
        });

        undo = new JMenuItem("Undo");
        undoHistory = new JMenuItem("Undo History");

        fileOpen.addActionListener(actionEvent -> {
            try {
                fileHandler.openFileNewWindow();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (RuntimeException e2) {
                JOptionPane.showMessageDialog(this, "Something went wrong", "Error message", JOptionPane.ERROR_MESSAGE);
            }
        });
        fileSave.addActionListener(actionEvent -> {
            try {
                fileHandler.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileExportBMP.addActionListener(actionEvent -> {
            try {
                BMPexporter.exportBMP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add menu items
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileExportBMP);

        edit.add(undo);
        edit.add(undoHistory);

        // Undo Listeners
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    canvas.Undo();
                } catch (UndoException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Undo error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        undoHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoHistory();
            }
        });

        // Edit the panels
        eastPanel.setLayout(new GridLayout(5, 2));
        westPanel.setLayout(new GridLayout(5, 1));

        // Call the toolboxes to build
        createButtonTools();

        // Add panels to control pane
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(westPanel, BorderLayout.WEST);
        getContentPane().add(eastPanel, BorderLayout.EAST);

        // Display the window
        setPreferredSize(new Dimension(800, 500));
        setLocation(new Point(200, 200));
        setJMenuBar(fileMenu);
        getContentPane().setBackground(Color.white);
        pack();
        setVisible(true);

        //Checks for window resize
        addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                //Canvas needs to take up the space between the west and east panels
                int sizeX=ev.getComponent().getWidth()-eastPanel.getWidth()-westPanel.getWidth();
                //height is not constrained in this way so just take same height as everyone
                int sizeY=ev.getComponent().getHeight();

                //add a leetle buffer
                sizeX-=10;
                sizeY-=10;

                //if the width is bigger than the height, the size of the square
                //canvas should be set to the height to maintain aspect ratio
                if (sizeX> sizeY){
                    //canvas bounds starts at where west panel ends
                    canvas.setBounds(westPanel.getWidth(),0, sizeY, sizeY);
                    //if the height is bigger than the width canvas should
                    //be set to width to maintain aspect ratio
                } else {
                    canvas.setBounds(westPanel.getWidth(),0,sizeX,sizeX);
                }

                //Redraw the canvas so the images will be resized
                canvas.refreshCanvas();
                canvas.readCommands();
                canvas.repaint();

            }
        });

        //ctrl z undo listener
        //
        int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
        //create action for key binding
        Action undoCommand = new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                //do nothing
                try {
                    canvas.Undo();
                } catch (UndoException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Undo error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        //create keybinding
        canvas.getInputMap(mapName).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "undo");
        //attach action to keybinding
        canvas.getActionMap().put("undo",
                undoCommand);

        //Checks for window resize
        addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                //Canvas needs to take up the space between the west and east panels
                int sizeX=ev.getComponent().getWidth()-eastPanel.getWidth()-westPanel.getWidth();
                //height is not constrained in this way so just take same height as everyone
                int sizeY=ev.getComponent().getHeight();

                //add a leetle buffer
                sizeX-=10;
                sizeY-=10;

                //if the width is bigger than the height, the size of the square
                //canvas should be set to the height to maintain aspect ratio
                if (sizeX> sizeY){
                    //canvas bounds starts at where west panel ends
                    canvas.setBounds(westPanel.getWidth(),westPanel.getY(), sizeY, sizeY);
                    //if the height is bigger than the width canvas should
                    //be set to width to maintain aspect ratio
                } else {
                    canvas.setBounds(westPanel.getWidth(),westPanel.getY(),sizeX,sizeX);
                }
                //Redraw the canvas so the images will be resized
                canvas.clean();
                canvas.readCommands();
                canvas.repaint();
            }
        });
    }

    public void createButtonTools() {
        JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn, undoBtn, historyBtn;
        JToggleButton fillBtn;
        JButton black, blue, red, green, otherColor;

        plotBtn = createButton("Plot");
        ellipseBtn = createButton("Ellipse");
        lineBtn = createButton("Line");
        polygonBtn = createButton("Polygon");
        rectangleBtn = createButton("Rectangle");
        undoBtn = createButton("Undo");
        historyBtn = createButton("History");
        fillBtn = makeFillButton();
        black = createButton("Black");
        blue = createButton("Blue");
        red = createButton("Red");
        green = createButton("Green");
        otherColor = createButton("Other");

        westPanel.add(plotBtn); westPanel.add(rectangleBtn);
        westPanel.add(lineBtn); westPanel.add(ellipseBtn); westPanel.add(polygonBtn);
        eastPanel.add(undoBtn); eastPanel.add(historyBtn);
        eastPanel.add(new JLabel("Step 1: Toggle Fill"));
        eastPanel.add(fillBtn); eastPanel.add(new JLabel("Step 2: Select Color"));
        eastPanel.add(black); eastPanel.add(blue); eastPanel.add(red);
        eastPanel.add(green); eastPanel.add(otherColor);
    }

    public JButton createButton(String name) {
        JButton tempBtn = new JButton(name);
        tempBtn.addActionListener(actionEvent -> {
            switch (name) {
                case "Undo":
                    try {
                        canvas.Undo();
                    } catch (UndoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Undo error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "History":
                    undoHistory();
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
                    if (color != null) {
                        String red = color.getRed() == 0 ? "00" : Integer.toHexString(color.getRed());
                        String green = color.getGreen() == 0 ? "00" : Integer.toHexString(color.getGreen());
                        String blue = color.getBlue() == 0 ? "00" : Integer.toHexString(color.getBlue());
                        String hex = "#" + red + green + blue;
                        canvas.addCommand(tool + " " + hex);
                    }
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

    public void undoHistory() {
        if (undoHisOpen) {
            //build the list
            undoHisOpen = false;
            JFrame guiHist = new JFrame();
            history.fillLabels();
            JList histList = new JList(history.labels);
            JScrollPane scrollPane = new JScrollPane(histList);
            histList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            histList.setLayoutOrientation(JList.VERTICAL);
            histList.addListSelectionListener(new ListSelectionListener() {
                //start listener
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        //pass through the desired index
                        history.displayPreview(histList.getSelectedIndex());
                    }
                }
            });
            guiHist.setSize(200, 150);
            guiHist.setLocation(new Point(50, 50));
            guiHist.setTitle("Undo History");
            guiHist.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    undoHisOpen = true;
                    history.windowCloseAction();
                }
            });
            Container contentPane = guiHist.getContentPane();
            contentPane.add(scrollPane, BorderLayout.CENTER);
            guiHist.setVisible(true);
        }
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }
}
