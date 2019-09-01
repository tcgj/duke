package duke.command;

import duke.Duke;
import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

/**
 * Represents a delete command entered by the user.
 *
 * @author Terence Chong Guang Jun
 */
public class DeleteCommand extends Command {
    DeleteCommand() {
        super();
    }

    private DeleteCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (arguments.size() < 1) {
            throw new DukeCommandException("Task number cannot be empty.");
        }
        Task task = taskList.getTask(Parser.parseInt(arguments.get(0)));
        taskList.removeTask(task);
        ui.sendMessage("Noted. I've removed this task:",
                Ui.INDENT + task,
                "Now you have " + taskList.getTaskCount() + " tasks in the list.");
        return Duke.CODE_CONTINUE;
    }

    @Override
    String[] getParams() {
        return new String[0];
    }

    @Override
    Command generate(List<String> arguments) {
        return new DeleteCommand(arguments);
    }
}
