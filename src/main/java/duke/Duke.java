package duke;

import duke.task.*;
import duke.ui.SimpleCliUi;
import duke.ui.Ui;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Duke {
    public static final Charset DUKE_CHARSET = StandardCharsets.UTF_8;
    public static final int CONTINUE_CODE = 0;
    public static final int EXIT_CODE = 1;
    private Ui ui;
    private TaskList taskList;
    private Path dataPath;

    public Duke(Ui ui, String saveLocation) {
        this.ui = ui;
        dataPath = Path.of(saveLocation).normalize();
    }

    public int mainFlow() throws IOException {
        String input = ui.getFullMessage();
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
                ui.sendTaskList(taskList);
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
            ui.sendMessage(new String[]{e.getMessage()});
        }
        return CONTINUE_CODE;
    }

    protected void run() {
        try {
            loadFromFile();

            ui.sendGreeting();
            int returnCode = CONTINUE_CODE;
            while (returnCode != EXIT_CODE) {
                returnCode = mainFlow();
            }
            ui.sendBye();
            saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.close();
    }

    protected void loadFromFile() throws IOException {
        File file = dataPath.toFile();
        if (file.exists()) {
            try (BufferedReader reader = Files.newBufferedReader(dataPath, DUKE_CHARSET)) {
                ArrayList<Task> list = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\\s\\|SPACE\\|\\s", -1);
                    boolean done = data[1].equals("1");
                    switch (data[0]) {
                    case "T":
                        list.add(new TodoTask(data[2], done));
                        break;
                    case "D":
                        Date deadline = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(data[3]);
                        list.add(new DeadlineTask(data[2], deadline, done));
                        break;
                    case "E":
                        Date datetime = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(data[3]);
                        list.add(new EventTask(data[2], datetime, done));
                        break;
                    default:
                        break;
                    }
                }
                taskList = new TaskList(list);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            taskList = new TaskList();
        }
    }

    protected void saveToFile() throws IOException {
        Files.createDirectories(dataPath.getParent());
        ArrayList<String> output = formatList();
        Files.write(
                dataPath,
                output,
                DUKE_CHARSET);
    }

    protected ArrayList<String> formatList() {
        ArrayList<String> outList = new ArrayList<>();
        String delimiter = " |SPACE| ";
        for (Task t : taskList.getList()) {
            if (t.hasArgs()) {
                outList.add(String.join(delimiter,
                        t.getType(),
                        t.isDone() ? "1" : "0",
                        t.getDescription(),
                        t.getArgs()));
            } else {
                outList.add(String.join(delimiter,
                        t.getType(),
                        t.isDone() ? "1" : "0",
                        t.getDescription()));
            }
        }
        return outList;
    }

    protected void addTodo(String taskInfo) throws DukeException {
        if (taskInfo.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }

        Task task = new TodoTask(taskInfo);
        taskList.addTask(task);
        ui.sendTaskAdded(task, taskList.getTaskCount());
    }

    protected void addDeadline(String taskInfo) throws DukeException {
        if (taskInfo.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }

        try {
            String[] details = taskInfo.split("\\s+/by\\s+", 2);
            Date deadline = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(details[1]);
            Task task = new DeadlineTask(details[0], deadline);
            taskList.addTask(task);
            ui.sendTaskAdded(task, taskList.getTaskCount());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! You did not specify a datetime.");
        } catch (ParseException e) {
            throw new DukeException("OOPS!!! Please specify a date in the form \'dd/mm/yyyy HHMM\'");
        }
    }

    protected void addEvent(String taskInfo) throws DukeException {
        if (taskInfo.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a event cannot be empty.");
        }

        try {
            String[] details = taskInfo.split("\\s+/at\\s+", 2);
            Date datetime = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(details[1]);
            Task task = new EventTask(details[0], datetime);
            taskList.addTask(task);
            ui.sendTaskAdded(task, taskList.getTaskCount());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! You did not specify a datetime.");
        } catch (ParseException e) {
            throw new DukeException("OOPS!!! Please specify a date in the form \'dd/mm/yyyy HHMM\'");
        }
    }

    protected Task getTask(String taskStr) throws DukeException {
        try {
            int taskNo = Integer.parseInt(taskStr);
            return taskList.getTask(taskNo - 1);
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
        task.setDone(true);
        ui.sendTaskDone(task);
    }

    protected void deleteTask(String index) throws DukeException {
        if (index.isEmpty()) {
            throw new DukeException("OOPS!!! I don't know which task to delete.");
        }

        Task task = getTask(index);
        taskList.removeTask(task);
        ui.sendTaskDeleted(task, taskList.getTaskCount());
    }

    public static void main(String[] args) {
        String path = "data/duke.txt";
        if (args.length > 0) {
            path = args[0];
        }
        Duke duke = new Duke(new SimpleCliUi(System.in, System.out, StandardCharsets.UTF_8), path);
        duke.run();
    }
}
