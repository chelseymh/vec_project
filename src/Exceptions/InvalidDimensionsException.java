package Exceptions;

/**
 * Checked exception to signal that the input given for a set of dimensions is incorrect.
 */
public class InvalidDimensionsException extends Exception {
    /**
     * Creates an <code>InvalidDimensionsException</code> with the specified detail message.
     * @param message The detail message (which is saved for later retrieval by the <code>getMessage</code> method from the <code>Throwable</code> class)
     */
    public InvalidDimensionsException(String message) {
        super(message);
    }
}
