package daming.command;

import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.TaskList;

import java.util.List;

/**
 * Represents a command entered by the user. Abstract base class to be inherited by
 * more specific command types.
 *
 * @author Terence Chong Guang Jun
 */
public abstract class Command {
    protected List<String> arguments;

    protected Command(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes this command, given the command center, task list and storage, and returns the response.
     *
     * @param commandCenter the command center to use.
     * @param storage the storage to use.
     * @param taskList the task list to use.
     * @return An array of strings representing the response by Daming.
     *     Each array index is a line.
     * @throws DamingException If this command fails to execute.
     */
    public abstract String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) throws DamingException;

    /**
     * Returns whether Daming should exit upon completing the command.
     * False by default.
     *
     * @return <code>true</code> if Daming should exit, <code>false</code> otherwise.
     */
    public boolean shouldExit() {
        return false;
    }
}
