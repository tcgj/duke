package duke.command;

import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.task.DeadlineTask;
import duke.task.Task;

import java.util.List;

/**
 * Represents a deadline command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class DeadlineCommand extends AddTaskCommand {
    public DeadlineCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    protected Task makeTask() throws DukeException {
        if (arguments.size() < 2) {
            throw new DukeCommandException("Deadline description cannot be empty.");
        }
        return new DeadlineTask(arguments.get(0), Parser.parseDate(arguments.get(1)));
    }
}
