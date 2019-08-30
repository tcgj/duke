package duke.command;

import duke.Duke;
import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.EventTask;
import duke.ui.Ui;

import java.util.List;

/**
 * Represents an event command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class EventCommand extends Command {
    EventCommand() {
        super();
    }

    private EventCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (arguments.size() < 2) {
            throw new DukeCommandException("Event description cannot be empty.");
        }
        Task task = new EventTask(arguments.get(0), Parser.parseDate(arguments.get(1)));
        taskList.addTask(task);
        ui.sendTaskAdded(task, taskList.getTaskCount());
        return Duke.CODE_CONTINUE;
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
