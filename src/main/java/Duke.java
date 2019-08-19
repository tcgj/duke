import java.io.*;
import java.util.ArrayList;

public class Duke {
    protected BufferedReader reader;
    protected PrintWriter writer;
    protected ArrayList<Task> list;

    public Duke(InputStream in, OutputStream out) {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new PrintWriter(out);
        list = new ArrayList<>();
    }

    public int mainFlow() throws IOException {
        String input = reader.readLine();
        if (input == null) {
            return 1;
        }

        String[] data = input.split("\\s+", 2);
        String[] details;
        switch (data[0]) {
            case "bye":
                return 1;
            case "list":
                displayTaskList();
                break;
            case "done":
                setTaskDone(Integer.parseInt(data[1]));
                break;
            case "todo":
                addTask(new TodoTask(data[1]));
                break;
            case "deadline":
                details = data[1].split("\\s+\\/by\\s+", 2);
                addTask(new DeadlineTask(details[0], details[1]));
                break;
            case "event":
                details = data[1].split("\\s+\\/at\\s+", 2);
                addTask(new EventTask(details[0], details[1]));
                break;
            default:
                break;
        }

        return 0;
    }

    protected void run() {
        try {
            greet();
            int returnCode = 0;
            while (returnCode == 0) {
                returnCode = mainFlow();
            }
            bye();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(writer);
        } finally {
            writer.close();
        }
    }

    protected void greet() {
        String helloText = "Hello! I'm Duke\nWhat can I do for you?";
        writer.println(helloText);
        writer.flush();
    }

    protected void bye() {
        writer.println("Bye. Hope to see you again soon!");
        writer.flush();
    }

    protected void addTask(Task task) {
        list.add(task);
        writer.println("Got it. I've added this task:");
        writer.println("  " + task);
        writer.println("Now you have " + list.size() + " tasks in the list.");
        writer.flush();
    }

    protected Task getTask(int taskNo) {
        return list.get(taskNo - 1);
    }

    protected void setTaskDone(int taskNo) {
        Task task = getTask(taskNo);
        task.markAsDone();
        writer.println("Nice! I've marked this task as done:");
        writer.println("  " + task);
        writer.flush();
    }

    protected void displayTaskList() {
        int listSize = list.size();
        writer.println("Here are the tasks in your list:");
        for (int i = 0; i < listSize; i++) {
            writer.println((i + 1) + "." + list.get(i));
        }
        writer.flush();
    }

    public static void main(String[] args) {
        Duke duke = new Duke(System.in, System.out);
        duke.run();
    }
}
