package duke.ui;

import java.nio.charset.StandardCharsets;

/**
 * Represents a simple command line user interface.
 *
 * @author Terence Chong Guang Jun
 */
public class SimpleCliUi extends Ui {
    private static final String REPLY_INDENT = "      ";
    private static final String LINE_WRAP = "    ------------------------------------------------------------";

    /**
     * Creates a new <code>SimpleCliUi</code> with the Standard Input and Output streams
     * and a UTF-8 character encoding.
     */
    public SimpleCliUi() {
        super(System.in, System.out, StandardCharsets.UTF_8);
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
}
