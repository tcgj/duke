package duke.command;

import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a find command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class FindCommand extends Command {
    public FindCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(TaskList taskList, Storage storage) throws DukeException {
        if (arguments.size() < 1) {
            throw new DukeCommandException("Search term cannot be empty.");
        }
        List<String> response = new LinkedList<>();
        response.add("Here are the matching tasks in your list:");
        for (Task task : taskList) {
            if (task.toString().contains(arguments.get(0))) {
                response.add(response.size() + ". " + task);
            }
        }

        return response.toArray(new String[response.size()]);
    }
}
