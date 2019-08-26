public class TodoTask extends Task {
    public TodoTask(String description) {
        this(description, false);
    }

    public TodoTask(String description, boolean done) {
        super(description, done);
    }

    @Override
    public boolean hasArgs() {
        return false;
    }

    @Override
    public String getArgs() {
        return "";
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
