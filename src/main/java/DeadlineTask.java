public class DeadlineTask extends Task {
    protected String deadline;

    public DeadlineTask(String description, String deadline) {
        this(description, deadline, false);
    }

    public DeadlineTask(String description, String deadline, boolean done) {
        super(description, done);
        this.deadline = deadline;
    }

    @Override
    public boolean hasArgs() {
        return true;
    }

    @Override
    public String getArgs() {
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
