package duke.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Represents the user interface that provides interaction with the user.
 *
 * @author Terence Chong Guang Jun
 */
public class Ui {
    public static final String INDENT = "  ";

    protected final BufferedReader reader;
    protected final PrintWriter writer;

    /**
     * Creates a new user interface with the given input and output streams.
     * The specified charset sets the character encoding for this <code>Ui</code>.
     *
     * @param in the input stream that user commands are read from.
     * @param out the output stream used to respond to the user.
     * @param charset the character encoding used for this <code>Ui</code>.
     */
    public Ui(InputStream in, OutputStream out, Charset charset) {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new PrintWriter(new OutputStreamWriter(out, charset));
    }

    /**
     * Gets the next entire message line sent by the user.
     *
     * @return the message string read from the user input.
     * @throws IOException If the user input cannot be read.
     */
    public String getMessage() throws IOException {
        return reader.readLine();
    }

    /**
     * Sends a multi-line message back to the user, where each line is
     * a <code>String</code> in the comma delimited argument.
     *
     * @param msg strings representing the lines of message to be sent.
     */
    public void sendMessage(String... msg) {
        for (String line : msg) {
            writer.println(line);
        }
        writer.flush();
    }

    /**
     * Closes the input and output streams used by this <code>Ui</code>.
     */
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
