package duke.ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class SimpleCliUi extends Ui {
    private static final String REPLY_INDENT = "    ";
    private static final String LINE_WRAP = "--------------------------------------------------";

    public SimpleCliUi(InputStream in, OutputStream out, Charset charset) {
        super(in, out, charset);
    }

    @Override
    public void sendMessage(String[] msg) {
        writer.println(REPLY_INDENT + LINE_WRAP);
        for (String line : msg) {
            writer.println(REPLY_INDENT + line);
        }
        writer.println(REPLY_INDENT + LINE_WRAP);
        writer.flush();
    }
}
