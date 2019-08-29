package duke.command;

import duke.Duke;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

public class ByeCommand extends Command {
    ByeCommand() {
        super();
    }

    private ByeCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) {
        ui.sendBye();
        return Duke.CODE_EXIT;
    }

    @Override
    String[] getParams() {
        return new String[0];
    }

    @Override
    Command generate(List<String> arguments) {
        return new ByeCommand(arguments);
    }
}
