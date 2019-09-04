package duke.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Represents a simple command line user interface.
 *
 * @author Terence Chong Guang Jun
 */
public class SimpleCliUi implements Ui {
    private static final String REPLY_INDENT = "      ";
    private static final String LINE_WRAP = "    ------------------------------------------------------------";
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Creates a new <code>SimpleCliUi</code> with the Standard Input and Output streams
     * and a UTF-8 character encoding.
     */
    public SimpleCliUi() {
        reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        writer = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    }

    @Override
    public String getMessage() throws IOException {
        return reader.readLine();
    }

    @Override
    public void sendMessage(String... msg) {
        writer.println(LINE_WRAP);
        for (String line : msg) {
            writer.println(REPLY_INDENT + line);
        }
        writer.println(LINE_WRAP);
        writer.flush();
    }

    @Override
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
