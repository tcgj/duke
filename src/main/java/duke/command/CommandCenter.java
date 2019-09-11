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
        registerCommand(EventCommand::new, new String[]{"\\s/at\\s"}, "event", "e");
        registerCommand(DeadlineCommand::new, new String[]{"\\s/by\\s"}, "deadline", "dead");
        registerCommand(FindCommand::new, new String[0], "find", "f", "search");
        registerCommand(ListCommand::new, new String[0], "list", "l");
        registerCommand(DoneCommand::new, new String[0], "done", "d", "completed", "complete");
        registerCommand(DeleteCommand::new, new String[0], "delete", "del");
        registerCommand(ByeCommand::new, new String[0], "bye", "b");
        registerCommand(AliasCommand::new, new String[]{"\\s"}, "alias", "a");
    }

    static boolean registerCommand(Function<List<String>, ? extends Command> ctr, String[] params, String... keywords) {
        for (String keyword : keywords) {
            if (commands.containsKey(keyword)) {
                return false;
            }
            commands.put(keyword, ctr);
            paramsList.put(keyword, params);
        }
        return true;
    }

    static Function<List<String>, ? extends Command> getCommand(String str) {
        return commands.get(str);
    }

    static String[] getParameters(String str) {
        return paramsList.get(str);
    }
}
