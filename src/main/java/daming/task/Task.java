package daming.task;

import java.util.Date;

/**
 * Represents a task to be done by the user. Abstract base class to be inherited by
 * more specific task types.
 *
 * @author Terence Chong Guang Jun
 */
public abstract class Task {
    private final String description;
    private boolean isDone;


    protected Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return <code>true</code>if task is completed, <code>false</code> otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Gets the <code>Date</code> object that represents the date stored in this task.
     *
     * @return the stored <code>Date</code> if it exists, <code>null</code> otherwise.
     */
    public abstract Date getDate();

    /**
     * Gets the string representation of the type of task.
     *
     * @return the string representation of the task type.
     * @see DeadlineTask
     * @see EventTask
     * @see TodoTask
     */
    public abstract String getType();

    /**
     * Gets the description of this task.
     *
     * @return the description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as completed.
     *
     * @param isDone Specifies if the task should be marked as complete.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", (isDone ? "✓" : "✘"), description);
    }
}