package daming.command;

import daming.exception.DamingCommandException;
import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.Task;
import daming.task.TaskList;

import java.util.List;

/**
 * Represents a done command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class DoneCommand extends Command {
    DoneCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) throws DamingException {
        if (arguments.size() < 1) {
            throw new DamingCommandException("Task number cannot be empty.");
        }
        Task task = taskList.getTask(Parser.parseInt(arguments.get(0)));
        task.setDone(true);
        return new String[]{
            "Nice! I've marked this task as done:",
            "    " + task
        };
    }
}
