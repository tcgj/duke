package duke.task;

import java.util.Date;

/**
 * Represents a todo task to be done by the user with a given description of the task.
 *
 * @author Terence Chong Guang Jun
 */
public class TodoTask extends Task {
    /**
     * Creates a new incomplete <code>TodoTask</code> with its task description.
     *
     * @param description the description of the task.
     */
    public TodoTask(String description) {
        this(description, false);
    }

    /**
     * Creates a new <code>TodoTask</code> with its task description.
     * May be set as incomplete or completed.
     *
     * @param description the description of the task.
     * @param isDone Specifies if the task should be marked as complete.
     */
    public TodoTask(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", getType(), super.toString());
    }
}
