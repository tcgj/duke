package duke.command;

import duke.Duke;
import duke.exception.DukeCommandException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TodoTask;
import duke.ui.Ui;

import java.util.List;

public class TodoCommand extends Command {
    TodoCommand() {
        super();
    }

    private TodoCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) throws DukeCommandException {
        if (arguments.size() < 1) {
            throw new DukeCommandException("Todo description cannot be empty.");
        }
        Task task = new TodoTask(arguments.get(0));
        taskList.addTask(task);
        ui.sendTaskAdded(task, taskList.getTaskCount());
        return Duke.CODE_CONTINUE;
    }

    @Override
    String[] getParams() {
        return new String[0];
    }

    @Override
    Command generate(List<String> arguments) {
        return new TodoCommand(arguments);
    }
}
