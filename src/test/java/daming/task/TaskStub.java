package daming.task;

import java.util.Date;

public class TaskStub extends Task {
    protected TaskStub(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public String getType() {
        return "TS";
    }
}
