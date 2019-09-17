package daming.command;

import daming.exception.DamingCommandException;
import daming.exception.DamingException;
import daming.task.EventTask;
import daming.task.Task;

import java.util.List;

/**
 * Represents an event command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class EventCommand extends AddTaskCommand {
    EventCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    protected Task makeTask() throws DamingException {
        if (arguments.size() < 2) {
            throw new DamingCommandException("Event arguments cannot be empty.");
        }
        return new EventTask(arguments.get(0), Parser.parseDate(arguments.get(1)));
    }
}
