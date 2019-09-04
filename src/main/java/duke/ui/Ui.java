package duke.ui;

import java.io.IOException;

/**
 * Represents the user interface that provides interaction with the user.
 *
 * @author Terence Chong Guang Jun
 */
public interface Ui {
    /**
     * Gets the next entire message line sent by the user.
     *
     * @return the message string read from the user input.
     * @throws IOException If the user input cannot be read.
     */
    String getMessage() throws IOException;

    /**
     * Sends a multi-line message back to the user, where each line is
     * a <code>String</code> in the comma delimited argument.
     *
     * @param msg strings representing the lines of message to be sent.
     */
    void sendMessage(String... msg);

    /**
     * Closes the ui.
     */
    void close();
}
