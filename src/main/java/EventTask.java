public class EventTask extends Task {
    protected String datetime;

    public EventTask(String description, String datetime) {
        this(description, datetime, false);
    }

    public EventTask(String description, String datetime, boolean done) {
        super(description, done);
        this.datetime = datetime;
    }

    @Override
    public boolean hasArgs() {
        return true;
    }

    @Override
    public String getArgs() {
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
