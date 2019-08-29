package duke.task;

import java.util.Date;

public abstract class Task {
    private final String description;
    private boolean isDone;

    protected Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public abstract Date getDate();

    public abstract String getType();

    public String getDescription() {
        return description;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", (isDone ? "\u2713" : "\u2718"), description);
    }
}