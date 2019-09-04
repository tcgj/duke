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
     * Executes this command, given the task list and storage, and returns the response.
     *
     * @param taskList the task list to use.
     * @param storage the storage to use.
     * @return An array of strings representing the response by Duke.
     *     Each array index is a line.
     * @throws DukeException If this command fails to execute.
     */
    public abstract String[] execute(TaskList taskList, Storage storage) throws DukeException;

    /**
     * Returns whether Duke should exit upon completing the command.
     * False by default.
     *
     * @return <code>true</code> if Duke should exit, <code>false</code> otherwise.
     */
    public boolean shouldExit() {
        return false;
    }

    abstract String[] getParams();

    abstract Command generate(List<String> arguments);
}
