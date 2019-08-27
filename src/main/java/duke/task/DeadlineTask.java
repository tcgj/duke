package duke.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeadlineTask extends Task {
    protected Date deadline;

    public DeadlineTask(String description, Date deadline) {
        this(description, deadline, false);
    }

    public DeadlineTask(String description, Date deadline, boolean done) {
        super(description, done);
        this.deadline = deadline;
    }

    @Override
    public boolean hasArgs() {
        return true;
    }

    @Override
    public String getArgs() {
        return new SimpleDateFormat("dd/MM/yyyy HHmm").format(deadline);
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
