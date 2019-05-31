package FileHandlers;

import paint_gui.Canvas;
import paint_gui.Gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * Handles the file reading and file writing operations necessary for opening and saving VEC files.
 */
public class FileHandler {
    private Canvas canvas;

    /**
     * Creates a FileHandler object to open and save files to and from the specified <code>Canvas</code>.
     * @param canvas The canvas from which to add or retrieve commands, dependent on whether the user is
     *               opening or saving files.
     */
    public FileHandler(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Creates a <code>JFileChooser</code> object for the user to choose a VEC file and opens it in a new window.
     * @throws IOException If something goes wrong with the <code>BufferedReader</code>
     */
    public void openFileNewWindow() throws IOException {
        JFileChooser chooser = getFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
            Gui gui = new Gui();
            gui.createGUI();
            gui.setLocation(300, 150);
            gui.getFileHandler().readFile(reader);
        }
    }

    /**
     * Iterates through the given <code>BufferedReader</code> and adds the lines to the commands list found in <code>Canvas</code>.
     * <p>Is public because the opened file has to be read into the newly created window instead of the one from which the
     * call to open a file was made.</p>
     * @param reader The reader containing the lines of the file chosen by the user.
     * @throws IOException If something goes wrong with the <code>BufferedReader</code>
     */
    public void readFile(BufferedReader reader) throws IOException {
        for (String lineFile = reader.readLine(); lineFile != null; lineFile = reader.readLine())
        {
            canvas.addCommand(lineFile);
        }
        canvas.readCommands();
    }

    /**
     * Saves a file containing the current commands of the canvas to whatever directory the user wants.
     * <p>Creates a <code>JFileChooser</code>, a <code>File</code> and a <code>BufferedWriter</code> to write the commands
     * of the canvas to a <code>File</code> and saves it to the directory chosen by the user.</p>
     * <p>A success message is shown, if the file is saved.</p>
     * @throws IOException If something goes wrong with the <code>BufferedWriter</code>
     * @throws FileAlreadyExistsException If a file with the given name already exists in the chosen directory.
     */
    public void saveFile() throws IOException {
        JFileChooser chooser = getFileChooser();
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

    //Helper method to create and return a JFileChooser that only accepts VEC files.
    private JFileChooser getFileChooser() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC file", "vec");
        chooser.setFileFilter(filter);
        return chooser;
    }
}
