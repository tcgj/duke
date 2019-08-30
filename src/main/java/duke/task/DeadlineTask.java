package duke.task;

import java.util.Date;

public class DeadlineTask extends Task {
    protected Date deadline;

    public DeadlineTask(String description, Date deadline) {
        this(description, deadline, false);
    }

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
