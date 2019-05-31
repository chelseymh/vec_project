package Exceptions;

/**
 * Checked exception to signal that something has gone wrong upon trying to undo a command.
 */
public class UndoException extends Exception {
    /**
     * Creates an <code>UndoException</code> with the specified detail message.
     * @param message The detail message (which is saved for later retrieval by the <code>getMessage</code> method from the <code>Throwable</code> class)
     */
    public UndoException(String message) {
        super(message);
    }
}
