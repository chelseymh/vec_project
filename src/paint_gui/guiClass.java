package paint_gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class guiClass extends JFrame /*implements ActionListener, KeyListener*/ {
    public static String toggledButton = null;
    private Box horizontalBoxPanel = Box.createHorizontalBox();
    private Box verticalBoxPanel = Box.createVerticalBox();
    Canvas canvas;
    private String tool = "PEN";
    private boolean fill = false;

    /**
     * Create the GUI and display it.
     */
    public void createGUI() {
        canvas = new Canvas(Color.white);

        JMenuBar fileMenu;
        JMenu file;
        JMenuItem fileNew, fileOpen, fileSave, fileExportBMP;

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

        addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {


                if (ev.getComponent().getWidth()> ev.getComponent().getHeight()){
                    canvas.setBounds(150,50,ev.getComponent().getHeight(),ev.getComponent().getHeight());
                    canvas.imageSizex=ev.getComponent().getHeight();
                    canvas.imageSizey=ev.getComponent().getHeight();
                } else {
                    canvas.setBounds(150,50,ev.getComponent().getWidth(),ev.getComponent().getWidth());
                    canvas.imageSizex=ev.getComponent().getWidth();
                    canvas.imageSizey=ev.getComponent().getWidth();
                }

                if (canvas.getImage()!=null) {
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
                int width = widthField.getText().equals("") ? canvas.imageSizex : Integer.parseInt(widthField.getText());
                int height = heightField.getText().equals("") ? canvas.imageSizey : Integer.parseInt(heightField.getText());
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
