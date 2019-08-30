package duke.task;

import java.util.Date;

/**
 * Represents an event task to be done by the user with a given description of the event.
 * as well as a datetime.
 *
 * @author Terence Chong Guang Jun
 */
public class EventTask extends Task {
    protected Date datetime;

    /**
     * Creates a new incomplete <code>EventTask</code> with its event description and a datetime.
     *
     * @param description the description of the event.
     * @param datetime the datetime for this event.
     */
    public EventTask(String description, Date datetime) {
        this(description, datetime, false);
    }

    /**
     * Creates a new <code>EventTask</code> with its event description and a datetime.
     * May be set as incomplete or completed.
     *
     * @param description the description of the event.
     * @param datetime the datetime for this event.
     * @param isDone Specifies if the task should be marked as complete.
     */
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
