package duke.exception;

/**
 * Represents a checked exception that occurred in <code>Duke</code>.
 *
 * @author Terence Chong Guang Jun
 */
public class DukeException extends Exception {
    /**
     * Creates a new <code>DukeException</code> with the given message.
     *
     * @param message the exception string.
     */
    public DukeException(String message) {
        super("Sorry! " + message);
    }
}
