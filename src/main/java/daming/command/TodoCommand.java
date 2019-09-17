package daming.command;

import daming.exception.DamingCommandException;
import daming.task.Task;
import daming.task.TodoTask;

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
    protected Task makeTask() throws DamingCommandException {
        if (arguments.size() < 1) {
            throw new DamingCommandException("Todo description cannot be empty.");
        }
        return new TodoTask(arguments.get(0));
    }
}
