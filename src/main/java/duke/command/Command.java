package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

/**
 * Represents a command entered by the user. Abstract base class to be inherited by
 * more specific command types.
 *
 * @author Terence Chong Guang Jun
 */
public abstract class Command {
    protected List<String> arguments;

    Command() {
    }

    Command(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes this command, given the task list, user interface, and storage.
     *
     * @param taskList the task list to use.
     * @param ui the ui to use.
     * @param storage the storage to use.
     * @return <code>Duke.CODE_CONTINUE</code> if <code>Duke</code> should continue
     * running, <code>Duke.CODE_EXIT</code> otherwise.
     * @throws DukeException If this command fails to execute.
     */
    public abstract int execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;
    abstract String[] getParams();
    abstract Command generate(List<String> arguments);
}
