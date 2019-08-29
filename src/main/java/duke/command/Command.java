package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

public abstract class Command {
    protected List<String> arguments;

    Command() {
    }

    Command(List<String> arguments) {
        this.arguments = arguments;
    }
    public abstract int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;
    abstract String[] getParams();
    abstract Command generate(List<String> arguments);
}
