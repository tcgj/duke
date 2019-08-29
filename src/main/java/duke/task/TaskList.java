package duke.task;

import duke.exception.DukeListException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskList implements Iterable<Task> {
    private List<Task> list;

    public TaskList() {
        this(new ArrayList<>());
    }

    public TaskList(List<Task> tasks) {
        list = tasks;
    }

    public int getTaskCount() {
        return list.size();
    }

    public Task getTask(int taskNo) throws DukeListException {
        try {
            return list.get(taskNo - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeListException("Task #" + taskNo + " does not exist.");
        }
    }

    public void addTask(Task task) {
        list.add(task);
    }

    public void removeTask(Task task) {
        list.remove(task);
    }

    @Override
    public Iterator<Task> iterator() {
        return list.iterator();
    }
}
