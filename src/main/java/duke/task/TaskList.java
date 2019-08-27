package duke.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
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

    public Task getTask(int index) {
        return list.get(index);
    }

    public void addTask(Task task) {
        list.add(task);
    }

    public void removeTask(Task task) {
        list.remove(task);
    }

    public List<Task> getList() {
        return list;
    }
}
