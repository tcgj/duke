package daming.command;

import daming.storage.Storage;
import daming.task.TaskList;

import java.io.IOException;
import java.util.List;

/**
 * Represents a bye command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class ByeCommand extends Command {
    ByeCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) {
        try {
            storage.save(commandCenter, taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{"Bye. Hope to see you again soon!"};
    }

    @Override
    public boolean shouldExit() {
        return true;
    }
}
