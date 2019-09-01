package duke.command;

import duke.Duke;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

/**
 * Represents a command to add new tasks given by the user. Abstract base class to be
 * inherited by specific add task commands.
 *
 * @author Terence Chong Guang Jun
 */
public abstract class AddTaskCommand extends Command {
    AddTaskCommand() {
        super();
    }

    AddTaskCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task newTask = makeTask();
        taskList.addTask(newTask);
        ui.sendMessage("Got it. I've added this task:",
                Ui.INDENT + newTask,
                "Now you have " + taskList.getTaskCount() + " tasks in the list.");

        return Duke.CODE_CONTINUE;
    }

    protected abstract Task makeTask() throws DukeException;
}
