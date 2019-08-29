package duke.command;

import duke.Duke;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

public class ListCommand extends Command {
    ListCommand() {
        super();
    }

    private ListCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) {
        ui.sendTaskList(taskList);
        return Duke.CODE_CONTINUE;
    }

    @Override
    String[] getParams() {
        return new String[0];
    }

    @Override
    Command generate(List<String> arguments) {
        return new ListCommand(arguments);
    }
}
