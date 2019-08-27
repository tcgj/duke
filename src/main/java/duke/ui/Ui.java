package duke.ui;

import duke.task.Task;
import duke.task.TaskList;

import java.io.*;
import java.nio.charset.Charset;

public class Ui {
    private static final String TASK_INDENT = "  ";

    protected final BufferedReader reader;
    protected final PrintWriter writer;

    public Ui(InputStream in, OutputStream out, Charset charset) {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new PrintWriter(new OutputStreamWriter(out, charset));
    }

    public String getFullMessage() throws IOException {
       return reader.readLine();
    }

    public void sendMessage(String[] msg) {
        for (String line : msg) {
            writer.println(line);
        }
        writer.flush();
    }

    // handle various types of reply
    public void sendGreeting() {
        sendMessage(new String[]{"Hello! I'm Duke",
                "What can I do for you?"});
    }

    public void sendBye() {
        sendMessage(new String[]{"Bye. Hope to see you again soon!"});
    }

    public void sendTaskAdded(Task task, int taskCount) {
        sendMessage(new String[]{"Got it. I've added this task:",
                TASK_INDENT + task,
                "Now you have " + taskCount + " tasks in the list."});
    }

    public void sendTaskDone(Task task) {
        sendMessage(new String[]{"Nice! I've marked this task as done:",
                TASK_INDENT + task});
    }

    public void sendTaskDeleted(Task task, int taskCount) {
        sendMessage(new String[]{"Noted. I've removed this task:",
                TASK_INDENT + task,
                "Now you have " + taskCount + " tasks in the list."});
    }

    public void sendTaskList(TaskList taskList) {
        int listSize = taskList.getTaskCount();
        String[] msg = new String[listSize + 1];
        msg[0] = "Here are the tasks in your list:";
        for (int i = 0; i < listSize; i++) {
            msg[i + 1] = (i + 1) + "." + taskList.getTask(i);
        }
        sendMessage(msg);
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(writer);
        } finally {
            writer.close();
        }
    }
}
