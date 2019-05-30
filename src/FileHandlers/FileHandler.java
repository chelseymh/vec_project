package FileHandlers;

import paint_gui.Canvas;
import paint_gui.guiClass;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class FileHandler {
    private Canvas canvas;

    public FileHandler(Canvas canvas) {
        this.canvas = canvas;
    }

    public void openFileNewWindow() throws IOException {
        guiClass gui = new guiClass();
        gui.createGUI();
        gui.setLocation(300, 150);
        BufferedReader reader = openFile(gui);
        if (reader != null) gui.getFileHandler().readFile(reader);
        else throw new RuntimeException("Reader null");
    }

    private BufferedReader openFile(guiClass gui) throws IOException {
        BufferedReader reader = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            reader = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
        } else gui.dispose();
        return reader;
    }

    public void readFile(BufferedReader reader) throws IOException {
        for (String lineFile = reader.readLine(); lineFile != null; lineFile = reader.readLine())
        {
            canvas.addCommand(lineFile);
        }
        canvas.readCommands();
    }

    public void saveFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            // Check the type of the file
            if (!filePath.endsWith(".vec")) fileToSave = new File(filePath.concat(".vec"));
            // Check if file already exists
            if (fileToSave.exists()) throw new FileAlreadyExistsException("File name already in use. Please rename or delete the existing file.");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
            for (String command : canvas.getCommands()) {
                writer.write(command);
                writer.newLine();
            }
            writer.close();
            JOptionPane.showMessageDialog(null, "File successfully saved to: \n" + fileToSave.getPath(), "Successful save", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
