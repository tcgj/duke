package daming.exception;

/**
 * Represents a checked exception that occurred in <code>Daming</code>.
 *
 * @author Terence Chong Guang Jun
 */
public class DamingException extends Exception {
    /**
     * Creates a new <code>DamingException</code> with the given message.
     *
     * @param message the exception string.
     */
    public DamingException(String message) {
        super("Sorry! " + message);
    }
}
