package duke.command;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

class CommandCenter {
    private static HashMap<String, Function<List<String>, ? extends Command>> commands;
    private static HashMap<String, String[]> paramsList;

    static {
        commands = new HashMap<>();
        paramsList = new HashMap<>();
        registerCommand(TodoCommand::new, new String[0], "todo", "t");
        registerCommand(EventCommand::new, new String[]{"/at"}, "event", "e");
        registerCommand(DeadlineCommand::new, new String[]{"/by"}, "deadline", "dead");
        registerCommand(FindCommand::new, new String[0], "find", "f", "search");
        registerCommand(ListCommand::new, new String[0], "list", "l");
        registerCommand(DoneCommand::new, new String[0], "done", "d", "completed", "complete");
        registerCommand(DeleteCommand::new, new String[0], "delete", "del");
        registerCommand(ByeCommand::new, new String[0], "bye", "b");
    }

    private static void registerCommand(Function<List<String>, ? extends Command> ctr, String[] params, String... keywords) {
        for (String keyword : keywords) {
            commands.put(keyword, ctr);
            paramsList.put(keyword, params);
        }
    }

    static Function<List<String>, ? extends Command> getCommand(String str) {
        return commands.get(str);
    }

    static String[] getParameters(String str) {
        return paramsList.get(str);
    }
}
