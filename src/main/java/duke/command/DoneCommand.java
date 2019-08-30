package duke.command;

import duke.Duke;
import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

public class DoneCommand extends Command {
    DoneCommand() {
        super();
    }

    private DoneCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (arguments.size() < 1) {
            throw new DukeCommandException("Task number cannot be empty.");
        }
        Task task = taskList.getTask(Parser.parseInt(arguments.get(0)));
        task.setDone(true);
        ui.sendTaskDone(task);
        return Duke.CODE_CONTINUE;
    }

    @Override
    String[] getParams() {
        return new String[0];
    }

    @Override
    Command generate(List<String> arguments) {
        return new DoneCommand(arguments);
    }
}