package duke.ui;

import duke.exception.DukeListException;
import duke.task.Task;
import duke.task.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Represents the user interface that provides interaction with the user.
 *
 * @author Terence Chong Guang Jun
 */
public class Ui {
    private static final String TASK_INDENT = "  ";

    protected final BufferedReader reader;
    protected final PrintWriter writer;

    /**
     * Creates a new user interface with the given input and output streams.
     * The specified charset sets the character encoding for this <code>Ui</code>.
     *
     * @param in the input stream that user commands are read from.
     * @param out the output stream used to respond to the user.
     * @param charset the character encoding used for this <code>Ui</code>.
     */
    public Ui(InputStream in, OutputStream out, Charset charset) {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new PrintWriter(new OutputStreamWriter(out, charset));
    }

    /**
     * Gets the next entire message line sent by the user.
     *
     * @return the message string read from the user input.
     * @throws IOException If the user input cannot be read.
     */
    public String getMessage() throws IOException {
       return reader.readLine();
    }

    /**
     * Sends a multi-line message back to the user, where each line is
     * a <code>String</code> in the comma delimited argument.
     *
     * @param msg strings representing the lines of message to be sent.
     */
    public void sendMessage(String... msg) {
        for (String line : msg) {
            writer.println(line);
        }
        writer.flush();
    }

    /**
     * Sends a fixed greeting message to the user.
     */
    public void sendGreeting() {
        sendMessage("Hello! I'm Duke",
                "What can I do for you?");
    }

    /**
     * Sends a fixed goodbye message to the user.
     */
    public void sendBye() {
        sendMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Sends a message specifying the <code>Task</code> that has been
     * added to the <code>TaskList</code>, as well as the number of tasks
     * currently in the list.
     *
     * @param task the <code>Task</code> that has been added.
     * @param taskCount the number of tasks currently in the list.
     */
    public void sendTaskAdded(Task task, int taskCount) {
        sendMessage("Got it. I've added this task:",
                TASK_INDENT + task,
                "Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Sends a message specifying the <code>Task</code> that has been
     * marked completed.
     *
     * @param task the <code>Task</code> that has been completed.
     */
    public void sendTaskDone(Task task) {
        sendMessage("Nice! I've marked this task as done:",
                TASK_INDENT + task);
    }

    /**
     * Sends a message specifying the <code>Task</code> that has been
     * deleted from the <code>TaskList</code>, as well as the number of tasks
     * remaining in the list.
     *
     * @param task the <code>Task</code> that has been deleted
     * @param taskCount the number of tasks remaining in the list.
     */
    public void sendTaskDeleted(Task task, int taskCount) {
        sendMessage("Noted. I've removed this task:",
                TASK_INDENT + task,
                "Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Sends a message displaying the list of tasks that are
     * currently in the given <code>TaskList</code>.
     *
     * @param taskList the list of tasks to be displayed
     */
    public void sendTaskList(TaskList taskList) {
        int listSize = taskList.getTaskCount();
        String[] msg = new String[listSize + 1];
        msg[0] = "Here are the tasks in your list:";
        for (int i = 1; i <= listSize; i++) {
            try {
                msg[i] = i + ". " + taskList.getTask(i);
            } catch (DukeListException e) {
                e.printStackTrace();
            }
        }
        sendMessage(msg);
    }

    /**
     * Closes the input and output streams used by this <code>Ui</code>.
     */
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
