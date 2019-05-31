package paint_gui;

import Exceptions.InvalidDimensionsException;
import FileHandlers.ExporterBMP;
import FileHandlers.FileHandler;
import Exceptions.UndoException;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Extends <code>javax.swing.JFrame</code> and is the main class for handling everything related to the graphical user interface.
 * It instantiates the other major classes <code>Canvas</code>, <code>undo</code>, and <code>History</code>.
 */
public class guiClass extends JFrame {
    /**
     * The name of the button that was last pressed.
     */
    public static String toggledButton = "";
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    private Canvas canvas;
    private History history;
    private Undo undo;
    private String tool = "PEN";
    private boolean fill = false;
    private boolean undoHisOpen;
    private FileHandler fileHandler;

    /**
     * Creates all the Swing components and adds the necessary action listeners and key bindings.
     * <p>The buttons of the window is made by a series of private helper methods.</p>
     * <p>An private method handles the resizing of the canvas to follow the main window.</p>
     * <p>A private <code>undoHistory</code> method handles the pop up window containing the
     * drawing history of the program, allowing the user to go back in time.</p>
     * Packs the frame and makes the frame visible.
     */
    public void createGUI() {

        JMenu file, edit;
        JMenuItem fileNew, fileOpen, fileSave, undoButton, undoHistory, fileExportBMP;
        edit = new JMenu("Edit");

        // Build two tool bars
        add(eastPanel);
        add(westPanel);

        // Create and set up window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Build top menu and first file dropdown
        JMenuBar fileMenu = new JMenuBar();
        fileMenu.setOpaque(true);
        fileMenu.setBackground(Color.white);
        fileMenu.setPreferredSize(new Dimension(200, 20));
        file = new JMenu("File");

        fileMenu.add(file);
        fileMenu.add(edit);

        // Build sub menu for fileOpen and fileSave
        fileNew = new JMenuItem("New file");
        fileOpen = new JMenuItem("Open file");
        fileSave = new JMenuItem("Save");
        fileExportBMP = new JMenuItem("Export as BMP");

        // Add action listeners
        fileNew.addActionListener(e -> {
            guiClass gui = new guiClass();
            gui.createGUI();
            gui.setLocation(300, 150);
        });

        undoButton = new JMenuItem("undo");
        undoHistory = new JMenuItem("undo History");

        // Add menu items
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileExportBMP);

        edit.add(undoButton);
        edit.add(undoHistory);

        // undo Listeners
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    undo.undo();
                } catch (UndoException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "undo error", JOptionPane.ERROR_MESSAGE);
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

       // Instantiate the canvas
        canvas = new Canvas(Color.white);
        history = new History(canvas);
        undo = new Undo(canvas);
        undoHisOpen = true;

        //Instantiate file handlers
        fileHandler = new FileHandler(canvas);
        ExporterBMP exporter = new ExporterBMP(canvas);

        fileOpen.addActionListener(actionEvent -> {
            try {
                fileHandler.openFileNewWindow();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        fileSave.addActionListener(actionEvent -> {
            try {
                fileHandler.saveFile();
            } catch (FileAlreadyExistsException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "File already exists", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileExportBMP.addActionListener(actionEvent -> {
            try {
                exporter.exportBMP();
            } catch (FileAlreadyExistsException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "File already exists", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidDimensionsException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid dimension input", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Call the toolboxes to build
        createButtonTools();

        // Add panels to control pane
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(westPanel, BorderLayout.WEST);
        getContentPane().add(eastPanel, BorderLayout.EAST);

        // Display the window
        setTitle("VEC Paint Program");
        setPreferredSize(new Dimension(800, 500));
        setLocation(new Point(100, 100));
        setMinimumSize(new Dimension(550, 242));
        setFocusableWindowState(true);
        setJMenuBar(fileMenu);
        getContentPane().setBackground(Color.white);
        pack();
        setVisible(true);

        //ctrl z undo listener
        int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;

        //create action for key binding
        Action undoCommand = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //do nothing
                try {
                    undo.undo();
                } catch (UndoException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "undo error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        //create keybinding
        canvas.getInputMap(mapName).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),"undo");
        //attach action to keybinding
        canvas.getActionMap().put("undo", undoCommand);

        //Checks for window resize
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent ev) {
                calculateAndSetCanvasBounds();
                //Redraw the canvas so the images will be resized
                canvas.readCommands();
                canvas.repaint();
            }
        });
        calculateAndSetCanvasBounds();
   }

    //Calculates and sets the bounds of the canvas to match with the size and resizing of the main frame
    private void calculateAndSetCanvasBounds() {
        //Canvas needs to take up the space between the west and east panels
        int sizeX = getWidth() - eastPanel.getWidth() - westPanel.getWidth();
        //Canvas needs to take up the space below fileMenu panel
        int sizeY = eastPanel.getHeight();

        //Add a leetle buffer
        sizeX -= 20;
        sizeY -= 20;
        //If the width is bigger than the height, the size of the square
        //canvas should be set to the height to maintain aspect ratio
        if (sizeX > sizeY) {
            //canvas bounds starts at where west panel ends
            canvas.setBounds(westPanel.getWidth() + 10, 10, sizeY, sizeY);
            //if the height is bigger than the width canvas should
            //be set to width to maintain aspect ratio
        } else {
            canvas.setBounds(westPanel.getWidth() + 10, 10, sizeX, sizeX);
        }
        canvas.refreshCanvas();
    }

    //Creates all the buttons by of the window by calling another helper method and adds them to the right JPanel
    private void createButtonTools() {
        JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn, undoBtn, historyBtn;
        JToggleButton fillBtn;
        JButton black, blue, red, green, otherColor;
        plotBtn = createButton("Plot");
        ellipseBtn = createButton("Ellipse");
        lineBtn = createButton("Line");
        polygonBtn = createButton("Polygon");
        rectangleBtn = createButton("Rectangle");
        undoBtn = createButton("undo");
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

    //Creates all the JButtons with the given name and adds a universal ActionListener for this type of button.
    private JButton createButton(String name) {
        JButton tempBtn = new JButton(name);
        tempBtn.addActionListener(actionEvent -> {
            switch (name) {
                case "undo":
                    try {
                        undo.undo();
                    } catch (UndoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "undo error", JOptionPane.ERROR_MESSAGE);
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
                    Color color = JColorChooser.showDialog(this, "Color chooser", null);
                    if (color != null) {
                        String red = color.getRed() == 0 ? "00" : Integer.toHexString(color.getRed());
                        String green = color.getGreen() == 0 ? "00" : Integer.toHexString(color.getGreen());
                        String blue = color.getBlue() == 0 ? "00" : Integer.toHexString(color.getBlue());
                        String hex = "#" + red + green + blue;
                        canvas.addCommand(tool + " " + hex);
                    }
                    break;
                default:
                    if (!toggledButton.equals(name)) {
                        toggledButton = name;
                        try {
                            //Get name of class to create from button name
                            Class shapeClass = Class.forName("Shapes."+ name);
                            //Instantiate from associated constructor and pass through parameters
                            Object shape = shapeClass.getConstructor(Canvas.class).newInstance(canvas);
                        } catch (Exception e){
                            System.out.println("Problem in the gui switch class");
                        }
                    }
                    break;
            }
        });
        return tempBtn;
    }

    //Creates the "Fill" JToggleButton and adds the required ActionListener
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

    //Creates the undo history window when the undo History button or menu item is pressed,
    //and adds window listeners to the undo History frame in order to prevent the user from using
    //the main window when the history is open.
    //Uses the History class to display the old commands.
    private void undoHistory() {
        if (undoHisOpen) {
            undoHisOpen = false;
            setEnabled(false);
            JFrame guiHist = new JFrame();
            guiHist.setFocusableWindowState(true);
            guiHist.requestFocus();
            history.fillLabels();
            JList histList = new JList(history.getLabels());
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
            guiHist.setTitle("undo History");

            // window listener for Frame
            guiHist.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    undoHisOpen = true;
                    setEnabled(true);
                    if(!histList.isSelectionEmpty()) {
                        history.windowCloseAction();

                    }
                    guiHist.dispose();
                }
            });
            guiHist.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    if (e.getOppositeWindow()==null) {
                        System.out.printf("gained focus");
                    }
                }
                @Override
                public void windowLostFocus(WindowEvent e) {
                    if (!undoHisOpen) {
                        JOptionPane.showMessageDialog(null, "undo History is still open. Please close to " +
                                "continue drawing.", "Window error", JOptionPane.ERROR_MESSAGE);
                        guiHist.requestFocus();
                    }
                }
            });
            Container contentPane = guiHist.getContentPane();
            contentPane.add(scrollPane, BorderLayout.CENTER);
            guiHist.setVisible(true);
        }
    }

    /**
     * Gets the <code>fileHandler</code>.
     * <p>Is called in the <code>FileHandler</code> class to open files in a new window.</p>
     * @return The FileHandler of a newly created guiClass in order to open a file in this window
     * and not in the one from where the action to open a new file has been made.
     */
    public FileHandler getFileHandler() {
        return fileHandler;
    }
}
