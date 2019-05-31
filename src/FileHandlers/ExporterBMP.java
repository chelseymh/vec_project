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

public class ExporterBMP {
    private Canvas canvas;

    public ExporterBMP(Canvas canvas) {
        this.canvas = canvas;
    }

    public void exportBMP() throws IOException, InvalidDimensionsException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP file", "bmp");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".bmp")) fileToSave = new File(filePath.concat(".bmp"));

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

    private BufferedImage imageToBufferedImage(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Draw the image onto the buffered image
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return bufferedImage;
    }
}
