package duke.task;

import java.util.Date;

public class TodoTask extends Task {
    public TodoTask(String description) {
        this(description, false);
    }

    public TodoTask(String description, boolean done) {
        super(description, done);
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
