package daming.command;

import daming.exception.DamingCommandException;
import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.TaskList;

import java.util.List;
import java.util.function.Function;

public class AliasCommand extends Command {
    AliasCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) throws DamingException {
        if (arguments.size() < 2) {
            throw new DamingCommandException("Alias arguments cannot be empty.");
        }

        String commandName = arguments.get(0);
        Function<List<String>, ? extends Command> ctr = commandCenter.getCommand(commandName);
        String[] params = commandCenter.getParameters(commandName);
        if (ctr == null || params == null) {
            throw new DamingCommandException("Command to set alias for doesn't exist.");
        }
        if (!commandCenter.registerAlias(commandName, arguments.get(1))) {
            throw new DamingCommandException("Make sure to set a valid alias for an official command.");
        }

        return new String[]{
                "Ok, I have set \'" + arguments.get(1) + "\' as an alias for \'" + commandName + "\'."
        };
    }
}
