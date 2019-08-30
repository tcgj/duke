package duke.exception;

/**
 * Represents a list exception that occurred in <code>Duke</code>.
 *
 * @author Terence Chong Guang Jun
 */
public class DukeListException extends DukeException {
    /**
     * Creates a new <code>DukeListException</code> with the given message.
     *
     * @param message the exception string.
     */
    public DukeListException(String message) {
        super(message);
    }
}
