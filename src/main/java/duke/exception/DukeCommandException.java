package duke.exception;

/**
 * Represents a command exception that occurred in <code>Duke</code>.
 *
 * @author Terence Chong Guang Jun
 */
public class DukeCommandException extends DukeException {
    /**
     * Creates a new <code>DukeCommandException</code> with the given message.
     *
     * @param message the exception string.
     */
    public DukeCommandException(String message) {
        super(message);
    }
}
