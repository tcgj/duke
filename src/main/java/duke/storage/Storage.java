package duke.storage;

import duke.Duke;
import duke.command.Parser;
import duke.exception.DukeParserException;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TodoTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents the storage manager for <code>Duke</code>. Reads and writes the task list to file.
 *
 * @author Terence Chong Guang Jun
 */
public class Storage {
    private final Charset charset;
    private final Path filePath;

    /**
     * Creates a new storage with the specified file path in UTF-8 encoding.
     *
     * @param path the path of the file for reading and writing.
     */
    public Storage(String path) {
        this(path, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new storage with the specified file path and character encoding.
     *
     * @param path the path of the file for reading and writing.
     * @param charset the character encoding to be used.
     */
    public Storage(String path, Charset charset) {
        filePath = Path.of(path).normalize();
        this.charset = charset;
    }

    /**
     * Reads the task list from file. If the file does not exist, an empty task list is returned.
     *
     * @return the task list read from file, or a new task list if the file does not exist.
     * @throws IOException If the file input cannot be read.
     */
    public TaskList load() throws IOException {
        File file = filePath.toFile();
        if (file.exists()) {
            try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
                List<Task> list = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\\s\\|DUKE_SPACER\\|\\s", -1);
                    boolean isDone = data[1].equals("1");
                    try {
                        switch (data[0]) {
                        case "T":
                            list.add(new TodoTask(data[2], isDone));
                            break;
                        case "D":
                            Date deadline = Parser.parseDate(data[3]);
                            list.add(new DeadlineTask(data[2], deadline, isDone));
                            break;
                        case "E":
                            Date datetime = Parser.parseDate(data[3]);
                            list.add(new EventTask(data[2], datetime, isDone));
                            break;
                        default:
                            break;
                        }
                    } catch (DukeParserException ignored) {
                        // If date cannot be parsed, too bad, we shall ignore that task.
                    }
                }
                return new TaskList(list);
            }
        } else {
            return new TaskList();
        }
    }

    /**
     * Writes the task list to file. Creates a new file if it does not exist along with any parent directories.
     *
     * @param taskList the task list to be written.
     * @throws IOException If the file cannot be written to.
     */
    public void save(TaskList taskList) throws IOException {
        Files.createDirectories(filePath.getParent());
        List<String> output = formatOutputList(taskList);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
            for (String line : output) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

    private List<String> formatOutputList(TaskList taskList) {
        List<String> outList = new ArrayList<>();
        String delimiter = " |DUKE_SPACER| ";
        for (Task t : taskList) {
            if (t instanceof TodoTask) {
                outList.add(String.join(delimiter,
                        t.getType(),
                        t.isDone() ? "1" : "0",
                        t.getDescription()));
            } else {
                outList.add(String.join(delimiter,
                        t.getType(),
                        t.isDone() ? "1" : "0",
                        t.getDescription(),
                        Duke.DATE_FORMAT.format(t.getDate())));
            }
        }
        return outList;
    }
}
