package duke.command;

import duke.Duke;
import duke.exception.DukeCommandException;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.DeadlineTask;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

public class DeadlineCommand extends Command {
    DeadlineCommand() {
        super();
    }

    private DeadlineCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (arguments.size() < 2) {
            throw new DukeCommandException("Deadline description cannot be empty.");
        }
        Task task = new DeadlineTask(arguments.get(0), Parser.parseDate(arguments.get(1)));
        taskList.addTask(task);
        ui.sendTaskAdded(task, taskList.getTaskCount());
        return Duke.CODE_CONTINUE;
    }

    @Override
    String[] getParams() {
        return new String[]{"/by"};
    }

    @Override
    Command generate(List<String> arguments) {
        return new DeadlineCommand(arguments);
    }
}
