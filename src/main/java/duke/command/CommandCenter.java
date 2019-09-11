package duke.command;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class CommandCenter {
    private static HashMap<String, Function<List<String>, ? extends Command>> commands;
    private static HashMap<String, String[]> paramsList;

    static {
        commands = new HashMap<>();
        paramsList = new HashMap<>();
        registerCommand("todo", TodoCommand::new, new String[0]);
        registerCommand("event", EventCommand::new, new String[]{"/at"});
        registerCommand("deadline", DeadlineCommand::new, new String[]{"/by"});
        registerCommand("find", FindCommand::new, new String[0]);
        registerCommand("list", ListCommand::new, new String[0]);
        registerCommand("done", DoneCommand::new, new String[0]);
        registerCommand("delete", DeleteCommand::new, new String[0]);
        registerCommand("bye", ByeCommand::new, new String[0]);
    }

    private static void registerCommand(String str, Function<List<String>, ? extends Command> ctr, String[] params) {
        commands.put(str, ctr);
        paramsList.put(str, params);
    }

    public static Function<List<String>, ? extends Command> getCommand(String str) {
        return commands.get(str);
    }

    public static String[] getParameters(String str) {
        return paramsList.get(str);
    }
}
