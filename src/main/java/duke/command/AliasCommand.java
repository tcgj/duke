package duke.command;

import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;

import java.util.List;
import java.util.function.Function;

public class AliasCommand extends Command {
    AliasCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(TaskList taskList, Storage storage) throws DukeException {
        if (arguments.size() < 2) {
            throw new DukeCommandException("Alias arguments cannot be empty.");
        }

        String commandName = arguments.get(0);
        Function<List<String>, ? extends Command> ctr = CommandCenter.getCommand(commandName);
        String[] params = CommandCenter.getParameters(commandName);
        if (ctr == null || params == null) {
            throw new DukeCommandException("Command to set alias for doesn't exist");
        }
        if (!CommandCenter.registerCommand(ctr, params, arguments.get(1))) {
            throw new DukeCommandException("This alias has already been used.");
        }

        return new String[]{
                "Ok, I have set \'" + arguments.get(1) + "\' as an alias for \'" + commandName + "\'."
        };
    }
}
