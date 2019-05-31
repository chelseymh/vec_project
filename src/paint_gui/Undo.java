package paint_gui;

import Exceptions.UndoException;

import java.util.List;

/**
 * Handles the undo functionality of the program.
 */
public class Undo {
    private Canvas canvas;

    /**
     * Creates an <code>Undo</code> object to handle undo operations on the specified <code>Canvas</code>.
     * @param canvas The canvas from which to retrieve commands
     */
    public Undo(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Removes the latest command from the canvas
     * @throws UndoException If there are no more commands to remove
     */
    public void undo() throws UndoException {
        List<String> commands = canvas.getCommands();
        int size = canvas.getCommands().size() -1;
        if (!canvas.getCommands().isEmpty()) {
            commands.remove(size);
            System.out.println("Undoed drawing operation");
        } else {
            throw new UndoException("No more undoes");
        }
        canvas.clean();
        canvas.readCommands();
        canvas.repaint();
    }
}
