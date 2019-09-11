package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;

import java.io.IOException;
import java.util.List;

/**
 * Represents a bye command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class ByeCommand extends Command {
    public ByeCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(TaskList taskList, Storage storage) {
        try {
            storage.save(taskList);
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
