package daming;

import daming.command.Command;
import daming.command.CommandCenter;
import daming.command.Parser;
import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.TaskList;
import daming.ui.SimpleCliUi;
import daming.ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Represents a task list chatbot named Daming.
 *
 * @author Terence Chong Guang Jun
 */
public class Daming {
    /**
     * Specifies the date format used by <code>Daming</code> for receiving date input.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy HHmm");

    private boolean willExit = false;
    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private CommandCenter commandCenter;

    /**
     * Creates a new <code>Daming</code> instance with a default filepath
     * set to data/ within the current working directory.
     */
    public Daming() {
        this("data");
    }

    /**
     * Creates a new <code>Daming</code> instance. The specified file path is used in
     * loading and saving the <code>TaskList</code> for this <code>Daming</code>.
     *
     * @param filePath Specifies the path where the <code>TaskList</code> is loaded from.
     */
    public Daming(String filePath) {
        this(new SimpleCliUi(), new Storage(filePath));
    }

    /**
     * Creates a new <code>Daming</code> instance with the given <code>Ui</code> and <code>Storage</code>.
     *
     * @param ui the ui for this <code>Daming</code>.
     *           Reads and prints replies to the user.
     * @param storage the storage for this <code>Daming</code>.
     *                Reads and writes the <code>TaskList</code> to file.
     */
    public Daming(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        commandCenter = CommandCenter.getInstance();

        try {
            taskList = storage.loadTasklist();
            storage.loadAliases(commandCenter);
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
            Command command = Parser.parse(commandCenter, input);
            assert command != null: "Command should exist if exception is not thrown";
            willExit = command.shouldExit();
            return command.execute(commandCenter, storage, taskList);
        } catch (DamingException e) {
            return new String[]{e.getMessage()};
        }
    }

    /**
     * Starts up Daming. Only stops when a <code>bye</code> command is read
     * or when an <code>IOException</code> is thrown.
     */
    public void run() {
        try {
            ui.sendMessage("Hello! I'm DA-MING",
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
     * Get Daming's response based on a single line of input.
     *
     * @param input the input string supplied to Daming.
     * @return the string response from Daming.
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
