package daming.exception;

/**
 * Represents a command exception that occurred in <code>Daming</code>.
 *
 * @author Terence Chong Guang Jun
 */
public class DamingCommandException extends DamingException {
    /**
     * Creates a new <code>DamingCommandException</code> with the given message.
     *
     * @param message the exception string.
     */
    public DamingCommandException(String message) {
        super(message);
    }
}
