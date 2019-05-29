package paint_gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static Object toggledButton = null;
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    Canvas canvas;
    private String tool = "PEN";
    private boolean fill = false;
    History history;
    private boolean undoHisOpen;

    /**
     * Create the GUI and display it.
     */
    public void createGUI() {
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
        fileExportBMP.addActionListener(actionEvent -> {
            try {
                exportBMP();
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
        eastPanel.setLayout(new GridLayout(5, 2));
        westPanel.setLayout(new GridLayout(5, 1));

        // Instantiate the canvas
        canvas = new Canvas(Color.white);
        history = new History(canvas);
        undoHisOpen = true;

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
            public void actionPerformed(ActionEvent e) {
                //do nothing
                canvas.Undo();
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
                    canvas.Undo();
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

    private void exportBMP() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP file", "bmp");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".bmp")) fileToSave = new File(filePath.concat(".bmp"));

            JTextField widthField = new JTextField(5);
            JTextField heightField = new JTextField(5);

            int result = getInput(widthField, heightField);
            if (result == JOptionPane.OK_OPTION) {
                int width = widthField.getText().equals("") ? canvas.getWidth() : Integer.parseInt(widthField.getText());
                int height = heightField.getText().equals("") ? canvas.getHeight() : Integer.parseInt(heightField.getText());
                BufferedImage bufferedImage = imageToBufferedImage(canvas.getImage(), width, height);
                ImageIO.write(bufferedImage, "BMP", fileToSave);
            }
        }
    }

    private int getInput(JTextField widthField, JTextField heightField) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Height:"));
        panel.add(heightField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);

        return JOptionPane.showConfirmDialog(this, panel, "Please enter height and width:", JOptionPane.OK_CANCEL_OPTION);
    }

    private BufferedImage imageToBufferedImage(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Draw the image onto the buffered image
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return bufferedImage;
    }
}
