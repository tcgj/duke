package daming.command;

import daming.exception.DamingException;
import daming.storage.Storage;
import daming.task.Task;
import daming.task.TaskList;

import java.util.List;

/**
 * Represents a command to add new tasks given by the user. Abstract base class to be
 * inherited by specific add task commands.
 *
 * @author Terence Chong Guang Jun
 */
public abstract class AddTaskCommand extends Command {
    AddTaskCommand(List<String> arguments) {
        super(arguments);
    }

    @Override
    public String[] execute(CommandCenter commandCenter, Storage storage, TaskList taskList) throws DamingException {
        Task newTask = makeTask();
        taskList.addTask(newTask);
        return new String[]{
            "Got it. I've added this task:",
            "    " + newTask,
            "Now you have " + taskList.getTaskCount() + " tasks in the list."
        };
    }

    protected abstract Task makeTask() throws DamingException;
}
