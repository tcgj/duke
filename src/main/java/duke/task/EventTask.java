package duke.task;

import java.util.Date;

public class EventTask extends Task {
    protected Date datetime;

    public EventTask(String description, Date datetime) {
        this(description, datetime, false);
    }

    public EventTask(String description, Date datetime, boolean isDone) {
        super(description, isDone);
        this.datetime = datetime;
    }

    @Override
    public Date getDate() {
        return datetime;
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (at: %s)", getType(), super.toString(), datetime);
    }
}
