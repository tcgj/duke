package duke.command;

import duke.Duke;
import duke.exception.DukeListException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

/**
 * Represents a list command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class ListCommand extends Command {
    ListCommand() {
        super();
    }

    private ListCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(TaskList taskList, Storage storage) {
        int listSize = taskList.getTaskCount();
        String[] msg = new String[listSize + 1];
        msg[0] = "Here are the tasks in your list:";
        for (int i = 1; i <= listSize; i++) {
            try {
                msg[i] = i + ". " + taskList.getTask(i);
            } catch (DukeListException e) {
                e.printStackTrace();
            }
        }

        return msg;
    }

    @Override
    String[] getParams() {
        return new String[0];
    }

    @Override
    Command generate(List<String> arguments) {
        return new ListCommand(arguments);
    }
}
