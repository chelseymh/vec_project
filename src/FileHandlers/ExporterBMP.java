package FileHandlers;

import Exceptions.InvalidDimensionsException;
import paint_gui.Canvas;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Handles all exports to the BMP file format.
 */
public class ExporterBMP {
    private Canvas canvas;

    /**
     * Creates an <code>ExporterBMP</code> object to export files with the image of the specified canvas.
     * @param canvas The <code>Canvas</code> from which to retrieve the image and transform it to the BMP format.
     */
    public ExporterBMP(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Exports the image of the canvas into a BMP file.
     * <p>Creates a <code>JFileChooser</code> for the user to choose where to save the file.</p>
     * <p>Then a <code>File</code> at this directory with the given name is created.</p>
     * <p>A pop up prompting the user to specify the dimensions of the BMP file is then created.
     * If the user does not input any numbers the image will be saved with the dimensions of the canvas.</p>
     * <p>The image contained in Canvas is then written to the BMP file format</p>
     * @throws IOException If something goes wrong with the encoding of the image
     */
    public void exportBMP() throws IOException, InvalidDimensionsException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP file", "bmp");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".bmp")) fileToSave = new File(filePath.concat(".bmp"));
            if (fileToSave.exists()) throw new FileAlreadyExistsException(fileToSave.getName() + " already exists. Please rename or delete the existing file.");

            JTextField widthField = new JTextField(5);
            JTextField heightField = new JTextField(5);
            int result = getInput(widthField, heightField);
            if (result == JOptionPane.OK_OPTION) {
                String widthInput = widthField.getText();
                String heightInput = heightField.getText();
                //Check the input of the user
                String number = "[0-9]+";
                if (!widthInput.matches(number) || !heightInput.matches(number)) throw new InvalidDimensionsException("Dimension has to be a whole number greater than zero.");
                int width = Integer.parseInt(widthInput);
                int height = Integer.parseInt(heightInput);
                BufferedImage bufferedImage = imageToBufferedImage(canvas.getImage(), width, height);
                ImageIO.write(bufferedImage, "BMP", fileToSave);
                JOptionPane.showMessageDialog(null, "File successfully exported and saved to: \n" + fileToSave.getPath(), "Successful export", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //Creates the input windows to specify the dimensions of the BMP file.
    private int getInput(JTextField widthField, JTextField heightField) {
        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Height:"));
        centerPanel.add(heightField);
        centerPanel.add(new JLabel("Width:"));
        centerPanel.add(widthField);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Please input whole numbers greater than zero"), BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return JOptionPane.showConfirmDialog(null, panel, "Input dimensions", JOptionPane.OK_CANCEL_OPTION);
    }

    //Transforms the image contained in Canvas to a BufferedImage
    private BufferedImage imageToBufferedImage(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Draw the image of canvas onto the newly created buffered image
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return bufferedImage;
    }
}
