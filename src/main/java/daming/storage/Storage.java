package daming.storage;

import daming.Daming;
import daming.command.CommandCenter;
import daming.command.Parser;
import daming.exception.DamingParserException;
import daming.task.DeadlineTask;
import daming.task.EventTask;
import daming.task.Task;
import daming.task.TaskList;
import daming.task.TodoTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the storage manager for <code>Daming</code>.
 * Reads and writes the task list to file.
 *
 * @author Terence Chong Guang Jun
 */
public class Storage {
    private static final String FILENAME_TASK = "daming.txt";
    private static final String FILENAME_ALIAS = "aliases.txt";
    private final Charset charset;
    private final Path dirPath;

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
        dirPath = Path.of(path).normalize();
        this.charset = charset;
    }

    /**
     * Reads the task list from file.
     * If the file does not exist, an empty task list is returned.
     *
     * @return the task list read from file, or a new task list if the file does not exist.
     * @throws IOException If the file input cannot be read.
     */
    public TaskList loadTasklist() throws IOException {
        Path taskPath = dirPath.resolve(FILENAME_TASK);
        if (!taskPath.toFile().exists()) {
            return new TaskList();
        }

        List<Task> list = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(taskPath, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s\\|DAMING_SPACER\\|\\s", -1);
                boolean isDone = data[1].equals("1");

                switch (data[0]) {
                case "T":
                    list.add(new TodoTask(data[2], isDone));
                    break;
                case "D":
                    try {
                        Date deadline = Parser.parseDate(data[3]);
                        list.add(new DeadlineTask(data[2], deadline, isDone));
                    } catch (DamingParserException ignored) {
                        // If date cannot be parsed, too bad, we shall ignore that task.
                    }
                    break;
                case "E":
                    try {
                        Date datetime = Parser.parseDate(data[3]);
                        list.add(new EventTask(data[2], datetime, isDone));
                    } catch (DamingParserException ignored) {
                        // If date cannot be parsed, too bad, we shall ignore that task.
                    }
                    break;
                default:
                    assert false: "Task type " + data[0] + " should not exist.";
                    break;
                }
            }
        }
        return new TaskList(list);
    }

    /**
     * Loads all aliases from file.
     * If the file does not exist, nothing happens.
     *
     * @param commandCenter the command center to load aliases into.
     * @throws IOException If the file input cannot be read.
     */
    public void loadAliases(CommandCenter commandCenter) throws IOException {
        Path aliasPath = dirPath.resolve(FILENAME_ALIAS);
        if (!aliasPath.toFile().exists()) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(aliasPath, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s", 2);
                commandCenter.addAllAliases(data[0], data[1].split("\\s", -1));
            }
        }
    }

    /**
     * Writes the task list and any command aliases to file.
     * Creates a new file if it does not exist along with any parent directories.
     *
     * @param taskList the task list to be written.
     * @throws IOException If the file cannot be written to.
     */
    public void save(CommandCenter commandCenter, TaskList taskList) throws IOException {
        Files.createDirectories(dirPath);
        List<String> taskListOutput = formatOutputList(taskList);
        List<String> aliasOutput = formatAliasList(commandCenter);

        writeToFile(dirPath.resolve(FILENAME_TASK), taskListOutput);
        writeToFile(dirPath.resolve(FILENAME_ALIAS), aliasOutput);
    }

    private void writeToFile(Path path, List<String> lines) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

    private List<String> formatOutputList(TaskList taskList) {
        List<String> outList = new ArrayList<>();
        String delimiter = " |DAMING_SPACER| ";
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
                        Daming.DATE_FORMAT.format(t.getDate())));
            }
        }
        return outList;
    }

    private List<String> formatAliasList(CommandCenter commandCenter) {
        HashMap<String, List<String>> aliasMap = commandCenter.getAliases();
        return aliasMap
                .entrySet()
                .stream()
                .map(entry -> {
                    List<String> out = new ArrayList<>();
                    out.add(entry.getKey());
                    out.addAll(entry.getValue());
                    return out;
                })
                .map(x -> String.join(" ", x))
                .collect(Collectors.toList());
    }
}
