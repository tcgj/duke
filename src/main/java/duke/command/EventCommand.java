package duke.command;

import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.task.Task;
import duke.task.EventTask;

import java.util.List;

/**
 * Represents an event command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class EventCommand extends AddTaskCommand {
    EventCommand() {
        super();
    }

    private EventCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    protected Task makeTask() throws DukeException {
        if (arguments.size() < 2) {
            throw new DukeCommandException("Event description cannot be empty.");
        }
        return new EventTask(arguments.get(0), Parser.parseDate(arguments.get(1)));
    }

    @Override
    String[] getParams() {
        return new String[]{"/at"};
    }

    @Override
    Command generate(List<String> arguments) {
        return new EventCommand(arguments);
    }
}
