package daming.task;

import daming.exception.DamingListException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskListTest {
    @Test
    void testGetTaskCount() {
        TaskList tl = new TaskList();
        tl.addTask(new TaskStub("Test1", false));
        tl.addTask(new TaskStub("Test2", false));
        assertEquals(2, tl.getTaskCount());
    }

    @Test
    void getTask_validTaskNumber_success() throws DamingListException {
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
        assertThrows(DamingListException.class, () -> tl.getTask(10));
    }
}