package paint_gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static String toggledButton = null;
    private Box horizontalBoxPanel = Box.createHorizontalBox();
    private Box verticalBoxPanel = Box.createVerticalBox();
    Canvas canvas;
    private String tool = "PEN";
    private boolean fill = false;
    History history;
    private boolean undoHisOpen;

    /**
     * Create the GUI and display it.
     */
    public void createGUI() {
        canvas = new Canvas(Color.white);

        JMenuBar fileMenu;
        JMenu file, edit;
        JMenuItem fileNew, fileOpen, fileSave, undo, undoHistory;
        edit = new JMenu("Edit");

        history = new History(canvas);
        undoHisOpen = true;

        // Build two tool bars
        JPanel verticalPanel = new JPanel();
        JPanel horizontalPanel = new JPanel();

        // Create and set up window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Build top menu and first file dropdown
        fileMenu = new JMenuBar();
        fileMenu.setOpaque(true);
        fileMenu.setBackground(Color.cyan);
        fileMenu.setPreferredSize(new Dimension(200, 20));
        file = new JMenu("File");

        // Add listener here
        fileMenu.add(file);
        fileMenu.add(edit);

        // Build sub menu for fileOpen and fileSave
        fileNew = new JMenuItem("New file");
        fileOpen = new JMenuItem("Open file");
        fileSave = new JMenuItem("Save");

        // Add action listeners
        fileNew.addActionListener(e -> {
            guiClass gui = new guiClass();
            gui.createGUI();
        });

        undo = new JMenuItem("Undo");
        undoHistory = new JMenuItem("Undo History");

        fileOpen.addActionListener(e -> {
            try {
                guiClass gui = new guiClass();
                gui.createGUI();
                BufferedReader reader = openFile();
                if (reader != null) gui.readFile(reader);
                else throw new RuntimeException("Reader null");
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
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);

        edit.add(undo);
        edit.add(undoHistory);

        // Undo Listeners
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.Undo();
            }
        });
        undoHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoHistory();
            }
        });

        // Edit the panels
        verticalPanel.setPreferredSize(new Dimension(100, 500));
        horizontalPanel.setPreferredSize(new Dimension(500, 50));

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

        //Checks for window resize
        addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                System.out.println("Window has been resized");
                System.out.println(canvas.getHeight());
                System.out.println(canvas.getWidth());
                //if the width is bigger than the height, the size of the square
                //canvas should be set to the height to maintain aspect ratio
                if (ev.getComponent().getWidth()> ev.getComponent().getHeight()){
                    canvas.setBounds(150,50,ev.getComponent().getHeight(),ev.getComponent().getHeight());
                    canvas.imageSizex=ev.getComponent().getHeight();
                    canvas.imageSizey=ev.getComponent().getHeight();
                    //if the height is bigger than the width canvas should
                    //be set to width to maintain aspect ratio
                } else {
                    canvas.setBounds(150,50,ev.getComponent().getWidth(),ev.getComponent().getWidth());
                    canvas.imageSizex=ev.getComponent().getWidth();
                    canvas.imageSizey=ev.getComponent().getWidth();
                }
                //Redraw the canvas so the images will be resized
                if (canvas.image!=null) {
                    canvas.clean();
                    canvas.readCommands();
                    canvas.repaint();
                }
            }
        });

        //ctrl z undo listener

        int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;


        Action undoCommand = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                //do nothing
                canvas.Undo();
            }
        };

        canvas.getInputMap(mapName).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK),
                "undo");
        canvas.getActionMap().put("undo",
                undoCommand);


    }

    public void createButtonTools() {
        JButton plotBtn, rectangleBtn, ellipseBtn, lineBtn, polygonBtn;
        JToggleButton fillBtn;
        JButton black, blue, red, green, otherColor;

        plotBtn = createButton("Plot");
        ellipseBtn = createButton("Ellipse");
        lineBtn = createButton("Line");
        polygonBtn = createButton("Polygon");
        rectangleBtn = createButton("Rectangle");



        fillBtn = makeFillButton();

        black = createButton("Black");
        blue = createButton("Blue");
        red = createButton("Red");
        green = createButton("Green");
        otherColor = createButton("Other");

        horizontalBoxPanel.add(plotBtn); horizontalBoxPanel.add(rectangleBtn);
        horizontalBoxPanel.add(lineBtn); horizontalBoxPanel.add(ellipseBtn);
        horizontalBoxPanel.add(polygonBtn);


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

    private BufferedReader openFile() throws IOException {
        BufferedReader reader = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            reader = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
        }
        return reader;
    }

    public void readFile(BufferedReader reader) throws IOException {
        for (String lineFile = reader.readLine(); lineFile != null; lineFile = reader.readLine())
        {
            canvas.addCommand(lineFile);
        }
        canvas.readCommands();
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
}
