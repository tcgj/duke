package duke.task;

import java.util.Date;

/**
 * Represents a deadline task to be done by the user with a given description of the task
 * as well as a deadline.
 *
 * @author Terence Chong Guang Jun
 */
public class DeadlineTask extends Task {
    protected Date deadline;

    /**
     * Creates a new incomplete <code>DeadlineTask</code> with its task description and a deadline.
     *
     * @param description the description of the task.
     * @param deadline the deadline for this task.
     */
    public DeadlineTask(String description, Date deadline) {
        this(description, deadline, false);
    }

    /**
     * Creates a new <code>DeadlineTask</code> with its task description and a deadline.
     * May be set as incomplete or completed.
     *
     * @param description the description of the task.
     * @param deadline the deadline for this task.
     * @param isDone Specifies if the task should be marked as complete.
     */
    public DeadlineTask(String description, Date deadline, boolean isDone) {
        super(description, isDone);
        this.deadline = deadline;
    }

    @Override
    public Date getDate() {
        return deadline;
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", getType(), super.toString(), deadline);
    }
}
