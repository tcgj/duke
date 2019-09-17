package daming.command;

import daming.exception.DamingCommandException;
import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.Task;
import daming.task.TaskList;

import java.util.List;

/**
 * Represents a delete command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class DeleteCommand extends Command {
    DeleteCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) throws DamingException {
        if (arguments.size() < 1) {
            throw new DamingCommandException("Task number cannot be empty.");
        }
        Task task = taskList.getTask(Parser.parseInt(arguments.get(0)));
        taskList.removeTask(task);
        return new String[]{
            "Noted. I've removed this task:",
            "    " + task,
            "Now you have " + taskList.getTaskCount() + " tasks in the list."
        };
    }
}
