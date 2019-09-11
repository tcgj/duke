package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;

import java.util.List;

/**
 * Represents a command to add new tasks given by the user. Abstract base class to be
 * inherited by specific add task commands.
 *
 * @author Terence Chong Guang Jun
 */
public abstract class AddTaskCommand extends Command {
    AddTaskCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(TaskList taskList, Storage storage) throws DukeException {
        Task newTask = makeTask();
        taskList.addTask(newTask);
        return new String[]{
            "Got it. I've added this task:",
            "    " + newTask,
            "Now you have " + taskList.getTaskCount() + " tasks in the list."
        };
    }

    protected abstract Task makeTask() throws DukeException;
}
