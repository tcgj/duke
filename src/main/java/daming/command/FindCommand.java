package daming.command;

import daming.exception.DamingCommandException;
import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.Task;
import daming.task.TaskList;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a find command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class FindCommand extends Command {
    FindCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) throws DamingException {
        if (arguments.size() < 1) {
            throw new DamingCommandException("Search term cannot be empty.");
        }
        List<String> response = new LinkedList<>();
        response.add("Here are the matching tasks in your list:");
        for (Task task : taskList) {
            if (task.toString().contains(arguments.get(0))) {
                response.add(response.size() + ". " + task);
            }
        }

        return response.toArray(new String[0]);
    }
}
