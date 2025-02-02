package daming.task;

import daming.exception.DamingListException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the task list that contains all individual tasks.
 *
 * @author Terence Chong Guang Jun
 */
public class TaskList implements Iterable<Task> {
    private List<Task> tasks;

    /**
     * Creates a new empty <code>TaskList</code>.
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Creates a new <code>TaskList</code> from the given list of tasks.
     *
     * @param tasks the list of tasks to form this <code>TaskList</code>.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets the number of tasks currently in this task list.
     *
     * @return the number of tasks in the list.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Gets the task specified by its current position in this task list.
     *
     * @param taskNo the position of the task.
     * @return the specified task.
     * @throws DamingListException If the task specified by the position does not exist.
     */
    public Task getTask(int taskNo) throws DamingListException {
        try {
            return tasks.get(taskNo - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DamingListException("Task #" + taskNo + " does not exist.");
        }
    }

    /**
     * Adds the given task to the task list.
     *
     * @param task the task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the given task from the task list.
     *
     * @param task the task to be removed.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
