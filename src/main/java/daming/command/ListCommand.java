package daming.command;

import daming.exception.DamingListException;
import daming.storage.Storage;
import daming.task.TaskList;

import java.util.List;

/**
 * Represents a list command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class ListCommand extends Command {
    ListCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) {
        int listSize = taskList.getTaskCount();
        String[] msg = new String[listSize + 1];
        msg[0] = "Here are the tasks in your list:";
        for (int i = 1; i <= listSize; i++) {
            try {
                msg[i] = i + ". " + taskList.getTask(i);
            } catch (DamingListException e) {
                e.printStackTrace();
            }
        }

        return msg;
    }
}
