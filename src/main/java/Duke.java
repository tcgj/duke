import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Duke {
    public static final Charset DUKE_CHARSET = StandardCharsets.UTF_8;
    public static final int CONTINUE_CODE = 0;
    public static final int EXIT_CODE = 1;
    protected BufferedReader reader;
    protected PrintWriter writer;
    protected ArrayList<Task> list;

    public Duke(InputStream in, OutputStream out) throws UnsupportedEncodingException {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new PrintWriter(new OutputStreamWriter(out, DUKE_CHARSET));
        list = new ArrayList<>();
    }

    public int mainFlow() throws IOException {
        String input = reader.readLine();
        if (input == null) {
            return EXIT_CODE;
        }

        try {
            String[] data = input.split("\\s+|$", 2);
            data[0] = data[0].toLowerCase();
            switch (data[0]) {
            case "bye":
                return EXIT_CODE;
            case "list":
                displayTaskList();
                break;
            case "done":
                setTaskDone(data[1]);
                break;
            case "todo":
                addTodo(data[1]);
                break;
            case "deadline":
                addDeadline(data[1]);
                break;
            case "event":
                addEvent(data[1]);
                break;
            case "delete":
                deleteTask(data[1]);
                break;
            default:
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            writer.println(e.getMessage());
            writer.flush();
        }
        return CONTINUE_CODE;
    }

    protected void run() {
        try {
            loadFromFile();

            greet();
            int returnCode = CONTINUE_CODE;
            while (returnCode != EXIT_CODE) {
                returnCode = mainFlow();
            }
            bye();
            saveToFile();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(writer);
        } finally {
            writer.close();
        }
    }

    protected void loadFromFile() throws IOException {
        File file = new File("data", "duke.txt");
        if (file.exists()) {
            try (BufferedReader reader = Files.newBufferedReader(file.toPath(), DUKE_CHARSET)) {
                String line = reader.readLine();
                while (line != null) {
                    String[] data = line.split("\\s|SPACE|\\s", -1);
                    writer.println(line);
                    writer.flush();
                }
            }
        }
    }

    protected void saveToFile() throws IOException {
        Files.createDirectory(Path.of("data"));
        Files.write(Path.of("data/duke.txt"), new ArrayList<String>(), DUKE_CHARSET, StandardOpenOption.CREATE);
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

    protected void addTodo(String taskInfo) throws DukeException {
        if (taskInfo.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }

        Task task = new TodoTask(taskInfo);
        list.add(task);
        writer.println("Got it. I've added this task:");
        writer.println("  " + task);
        writer.println("Now you have " + list.size() + " tasks in the list.");
        writer.flush();
    }

    protected void addDeadline(String taskInfo) throws DukeException {
        if (taskInfo.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }

        try {
            String[] details = taskInfo.split("\\s+/by\\s+", 2);
            Task task = new DeadlineTask(details[0], details[1]);
            list.add(task);
            writer.println("Got it. I've added this task:");
            writer.println("  " + task);
            writer.println("Now you have " + list.size() + " tasks in the list.");
            writer.flush();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! You did not specify a datetime.");
        }
    }

    protected void addEvent(String taskInfo) throws DukeException {
        if (taskInfo.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a event cannot be empty.");
        }

        try {
            String[] details = taskInfo.split("\\s+/at\\s+", 2);
            Task task = new EventTask(details[0], details[1]);
            list.add(task);
            writer.println("Got it. I've added this task:");
            writer.println("  " + task);
            writer.println("Now you have " + list.size() + " tasks in the list.");
            writer.flush();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! You did not specify a datetime.");
        }
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

    protected void setTaskDone(String index) throws DukeException {
        if (index.isEmpty()) {
            throw new DukeException("OOPS!!! I don't know which task has been done.");
        }

        Task task = getTask(index);
        task.markAsDone();
        writer.println("Nice! I've marked this task as done:");
        writer.println("  " + task);
        writer.flush();
    }

    protected void deleteTask(String index) throws DukeException {
        if (index.isEmpty()) {
            throw new DukeException("OOPS!!! I don't know which task to delete.");
        }

        Task task = getTask(index);
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
        try {
            Duke duke = new Duke(System.in, System.out);
            duke.run();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
