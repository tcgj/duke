package duke;

import duke.command.Command;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.SimpleCliUi;
import duke.ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Represents a task list chatbot named Duke.
 *
 * @author Terence Chong Guang Jun
 */
public class Duke {
    /**
     * Specifies the date format used by <code>Duke</code> for receiving date input.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy HHmm");

    private boolean willExit = false;
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    /**
     * Creates a new <code>Duke</code> instance with a default filepath
     * set to data/duke.txt within the current working directory.
     */
    public Duke() {
        this("data/duke.txt");
    }

    /**
     * Creates a new <code>Duke</code> instance. The specified file path is used in
     * loading and saving the <code>TaskList</code> for this <code>Duke</code>.
     *
     * @param filePath Specifies the path where the <code>TaskList</code> is loaded from.
     */
    public Duke(String filePath) {
        this(new SimpleCliUi(), new Storage(filePath));
    }

    /**
     * Creates a new <code>Duke</code> instance with the given <code>Ui</code> and <code>Storage</code>.
     *
     * @param ui the ui for this <code>Duke</code>.
     *           Reads and prints replies to the user.
     * @param storage the storage for this <code>Duke</code>.
     *                Reads and writes the <code>TaskList</code> to file.
     */
    public Duke(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;

        try {
            taskList = storage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert ui != null: "UI should not be null";
        assert storage != null: "Storage should not be null";
        assert taskList != null: "TaskList should not be null";
    }

    private String[] processLine(String input) throws IOException {
        if (input == null) {
            throw new IOException("Input cannot be read.");
        }

        try {
            Command command = Parser.parse(input);
            assert command != null: "Command should exist if exception is not thrown";
            willExit = command.shouldExit();
            return command.execute(taskList, storage);
        } catch (DukeException e) {
            return new String[]{e.getMessage()};
        }
    }

    /**
     * Starts up Duke. Only stops when a <code>bye</code> command is read
     * or when an <code>IOException</code> is thrown.
     */
    public void run() {
        try {
            ui.sendMessage("Hello! I'm Duke",
                    "What can I do for you?");
            while (!willExit) {
                String[] response = processLine(ui.getMessage());
                ui.sendMessage(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.close();
    }

    /**
     * Get Duke's response based on a single line of input.
     *
     * @param input the input string supplied to Duke.
     * @return the string response from Duke.
     */
    public String getResponse(String input) {
        try {
            String[] response = processLine(input);
            return String.join("\n", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
