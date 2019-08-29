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

public class Duke {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy HHmm");
    public static final int CODE_CONTINUE = 0;
    public static final int CODE_EXIT = 1;

    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    public Duke(String filePath) {
        this(new SimpleCliUi(), new Storage(filePath));
    }

    public Duke(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
    }

    private int mainFlow() throws IOException {
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

    public void run() {
        try {
            taskList = storage.load();

            ui.sendGreeting();
            int returnCode = CODE_CONTINUE;
            while (returnCode != CODE_EXIT) {
                returnCode = mainFlow();
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
