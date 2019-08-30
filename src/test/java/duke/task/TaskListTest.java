package duke.task;

import duke.exception.DukeListException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    @Test
    void testGetTaskCount() {
        TaskList tl = new TaskList();
        tl.addTask(new TaskStub("Test1", false));
        tl.addTask(new TaskStub("Test2", false));
        assertEquals(2, tl.getTaskCount());
    }

    @Test
    void getTask_validTaskNumber_success() throws DukeListException {
        TaskList tl = new TaskList();
        Task ts = new TaskStub("Test1", false);
        tl.addTask(ts);
        tl.addTask(new TaskStub("Test2", false));
        assertEquals(ts, tl.getTask(1));
    }

    @Test
    void getTask_inValidTaskNumber_throwsException() {
        TaskList tl = new TaskList();
        tl.addTask(new TaskStub("Test1", false));
        tl.addTask(new TaskStub("Test2", false));
        assertThrows(DukeListException.class, () -> tl.getTask(10));
    }
}