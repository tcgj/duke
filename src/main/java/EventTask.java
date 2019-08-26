import java.util.Date;

public class EventTask extends Task {
    protected Date datetime;

    public EventTask(String description, Date datetime) {
        this(description, datetime, false);
    }

    public EventTask(String description, Date datetime, boolean done) {
        super(description, done);
        this.datetime = datetime;
    }

    @Override
    public boolean hasArgs() {
        return true;
    }

    @Override
    public String getArgs() {
        return datetime.toString();
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
