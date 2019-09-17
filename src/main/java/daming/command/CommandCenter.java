package daming.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Represents the store for all commands and aliases in Daming.
 *
 * @author Chong Guang Jun
 */
public class CommandCenter {
    private static CommandCenter instance = new CommandCenter();
    private HashMap<String, Function<List<String>, ? extends Command>> commands;
    private HashMap<String, String[]> paramsList;
    private HashMap<String, List<String>> commandAliases;

    private CommandCenter() {
        commands = new HashMap<>();
        paramsList = new HashMap<>();
        commandAliases = new HashMap<>();
        init();
    }

    private void init() {
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

    private void registerCommand(Function<List<String>, ? extends Command> ctr, String[] params,
            String commandName, String... aliases) {
        List<String> aliasList = new ArrayList<>();
        commands.put(commandName, ctr);
        paramsList.put(commandName, params);
        commandAliases.put(commandName, aliasList);
        addAllAliases(commandName, aliases);
    }

    boolean registerAlias(String commandName, String alias) {
        List<String> aliasList = commandAliases.get(commandName);
        if (aliasList == null || getCommand(alias) != null) {
            return false;
        }

        aliasList.add(alias);
        commands.put(alias, getCommand(commandName));
        paramsList.put(alias, getParameters(commandName));
        return true;
    }

    /**
     * Adds all aliases given in the comma-separated arguments to the specified command.
     *
     * @param commandName the command to add the aliases to.
     * @param aliases the comma-separated string list of aliases.
     */
    public void addAllAliases(String commandName, String... aliases) {
        for (String alias : aliases) {
            registerAlias(commandName, alias);
        }
    }

    Function<List<String>, ? extends Command> getCommand(String str) {
        return commands.get(str);
    }

    String[] getParameters(String str) {
        return paramsList.get(str);
    }

    /**
     * Gets a map of the command name to the list of aliases for that command.
     *
     * @return the map of command name to alias list.
     */
    public HashMap<String, List<String>> getAliases() {
        return commandAliases;
    }

    public static CommandCenter getInstance() {
        return instance;
    }
}
