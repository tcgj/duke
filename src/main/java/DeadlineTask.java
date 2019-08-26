import java.util.Date;

public class DeadlineTask extends Task {
    protected Date by;

    public DeadlineTask(String description, Date by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
