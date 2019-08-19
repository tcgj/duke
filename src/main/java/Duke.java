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
        if (input == null || input.equals("bye")) {
            return 1;
        }

        try {
            String[] data = input.split("\\s+", 2);
            switch (data[0].toLowerCase()) {
                case "list":
                    displayTaskList();
                    break;
                case "done":
                    setTaskDone(data);
                    break;
                case "todo": case "deadline": case "event":
                    addTask(data);
                    break;
                case "delete":
                    deleteTask(data);
                    break;
                default:
                    throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            writer.println(e.getMessage());
            writer.flush();
        } finally {
            return 0;
        }
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

    protected void addTask(String[] taskInfo) throws DukeException {
        String command = taskInfo[0].toLowerCase();

        if (taskInfo.length != 2 || taskInfo[1].isEmpty()) {
            throw new DukeException("OOPS!!! The description of a " + command + " cannot be empty.");
        }

        Task task;
        String[] details;
        try {
            if (command.equals("todo")) {
                task = new TodoTask(taskInfo[1]);
            } else if (command.equals("deadline")) {
                details = taskInfo[1].split("\\s+\\/by\\s+", 2);
                task = new DeadlineTask(details[0], details[1]);
            } else {
                details = taskInfo[1].split("\\s+\\/at\\s+", 2);
                task = new EventTask(details[0], details[1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! You did not specify a datetime.");
        }

        list.add(task);
        writer.println("Got it. I've added this task:");
        writer.println("  " + task);
        writer.println("Now you have " + list.size() + " tasks in the list.");
        writer.flush();
    }

    protected Task getTask(String taskStr) throws DukeException {
        try {
            int taskNo = Integer.parseInt(taskStr);
            return list.get(taskNo - 1);
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! That is not a valid list number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! This task does not exist.");
        }
    }

    protected void setTaskDone(String[] data) throws DukeException {
        if (data.length != 2) {
            throw new DukeException("OOPS!!! I don't know which task has been done.");
        }

        Task task = getTask(data[1]);
        task.markAsDone();
        writer.println("Nice! I've marked this task as done:");
        writer.println("  " + task);
        writer.flush();
    }

    protected void deleteTask(String[] data) throws DukeException {
        if (data.length != 2) {
            throw new DukeException("OOPS!!! I don't know which task to delete.");
        }

        Task task = getTask(data[1]);
        list.remove(task);
        writer.println("Noted. I've removed this task:");
        writer.println("  " + task);
        writer.println("Now you have " + list.size() + " tasks in the list.");
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
