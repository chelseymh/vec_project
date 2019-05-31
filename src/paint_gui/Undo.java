package paint_gui;

import Exceptions.UndoException;

import java.util.List;

public class Undo {
    private Canvas canvas;

    public Undo(Canvas canvas) {
        this.canvas = canvas;
    }

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
