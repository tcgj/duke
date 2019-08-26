public abstract class Task {
    protected final String description;
    protected boolean done;

    public Task(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public abstract boolean hasArgs();

    public abstract String getType();

    public String getDescription() {
        return description;
    }

    public abstract String getArgs();

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", (done ? "\u2713" : "\u2718"), description);
    }
}