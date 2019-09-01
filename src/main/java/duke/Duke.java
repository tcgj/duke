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

    /**
     * Specifies that <code>Duke</code> should continue to read the next command.
     */
    public static final int CODE_CONTINUE = 0;

    /**
     * Specifies that <code>Duke</code> should say bye and exit.
     */
    public static final int CODE_EXIT = 1;

    private Ui ui;
    private Storage storage;
    private TaskList taskList;

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
     * @param ui the ui for this <code>Duke</code>. Reads and prints replies to the user.
     * @param storage the storage for this <code>Duke</code>. Reads and writes the <code>TaskList</code> to file.
     */
    public Duke(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
    }

    private int executeMainFlow() throws IOException {
        String input = ui.getMessage();
        if (input == null) {
            return CODE_EXIT;
        }

        try {
            Command command = Parser.parse(input);
            return command.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.sendMessage(e.getMessage());
        }
        return CODE_CONTINUE;
    }

    /**
     * Starts up Duke. Only stops when a <code>bye</code> command is read
     * or when an <code>IOException</code> is thrown.
     */
    public void run() {
        try {
            taskList = storage.load();

            ui.sendMessage("Hello! I'm Duke",
                    "What can I do for you?");
            int returnCode = CODE_CONTINUE;
            while (returnCode != CODE_EXIT) {
                returnCode = executeMainFlow();
            }
            storage.save(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.close();
    }

    public static void main(String[] args) {
        String path = "data/duke.txt";
        if (args.length > 0) {
            path = args[0];
        }
        Duke duke = new Duke(path);
        duke.run();
    }
}
