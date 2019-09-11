package duke.command;

import duke.exception.DukeCommandException;
import duke.task.Task;
import duke.task.TodoTask;

import java.util.List;

/**
 * Represents a todo command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class TodoCommand extends AddTaskCommand {
    TodoCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    protected Task makeTask() throws DukeCommandException {
        if (arguments.size() < 1) {
            throw new DukeCommandException("Todo description cannot be empty.");
        }
        return new TodoTask(arguments.get(0));
    }
}
