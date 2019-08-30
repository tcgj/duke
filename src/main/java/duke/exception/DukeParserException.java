package duke.exception;

/**
 * Represents a parser exception that occurred in <code>Duke</code>.
 *
 * @author Terence Chong Guang Jun
 */
public class DukeParserException extends DukeException {
    /**
     * Creates a new <code>DukeParserException</code> with the given message.
     *
     * @param message the exception string.
     */
    public DukeParserException(String message) {
        super(message);
    }
}
