package daming.command;

import daming.exception.DamingCommandException;
import daming.exception.DamingException;
import daming.task.DeadlineTask;
import daming.task.Task;

import java.util.List;

/**
 * Represents a deadline command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class DeadlineCommand extends AddTaskCommand {
    DeadlineCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    protected Task makeTask() throws DamingException {
        if (arguments.size() < 2) {
            throw new DamingCommandException("Deadline arguments cannot be empty.");
        }
        return new DeadlineTask(arguments.get(0), Parser.parseDate(arguments.get(1)));
    }
}
