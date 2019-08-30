package duke.task;

import duke.exception.DukeListException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskList implements Iterable<Task> {
    private List<Task> tasks;

    public TaskList() {
        this(new ArrayList<>());
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public Task getTask(int taskNo) throws DukeListException {
        try {
            return tasks.get(taskNo - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeListException("Task #" + taskNo + " does not exist.");
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
