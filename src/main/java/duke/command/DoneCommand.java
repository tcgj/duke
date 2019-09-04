package duke.command;

import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;

import java.util.List;

/**
 * Represents a done command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class DoneCommand extends Command {
    DoneCommand() {
        super();
    }

    private DoneCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(TaskList taskList, Storage storage) throws DukeException {
        if (arguments.size() < 1) {
            throw new DukeCommandException("Task number cannot be empty.");
        }
        Task task = taskList.getTask(Parser.parseInt(arguments.get(0)));
        task.setDone(true);
        return new String[]{
            "Nice! I've marked this task as done:",
            "    " + task
        };
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
