package duke.command;

import duke.Duke;
import duke.exception.DukeParserException;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * Represents a parser for string inputs.
 *
 * @author Terence Chong Guang Jun
 */
public class Parser {
    private static HashMap<String, CommandBuilder> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put("todo", CommandBuilder.getCommandBuilder(new TodoCommand()));
        commandMap.put("event", CommandBuilder.getCommandBuilder(new EventCommand()));
        commandMap.put("deadline", CommandBuilder.getCommandBuilder(new DeadlineCommand()));
        commandMap.put("find", CommandBuilder.getCommandBuilder(new FindCommand()));
        commandMap.put("list", CommandBuilder.getCommandBuilder(new ListCommand()));
        commandMap.put("done", CommandBuilder.getCommandBuilder(new DoneCommand()));
        commandMap.put("delete", CommandBuilder.getCommandBuilder(new DeleteCommand()));
        commandMap.put("bye", CommandBuilder.getCommandBuilder(new ByeCommand()));
    }

    /**
     * Parses the entire given input string, and returns the respective command.
     * The input arguments are stored in the command object.
     *
     * @param input the string to be parsed.
     * @return the command that represents the user input.
     * @throws DukeParserException If the command provided is invalid or the required date is not specified.
     */
    public static Command parse(String input) throws DukeParserException {
        String[] data = input.trim().split("\\s+", 2);
        CommandBuilder cmdBuilder = commandMap.get(data[0]);
        if (cmdBuilder == null) {
            throw new DukeParserException("I don't understand what that means.");
        }
        if (data.length != 2) {
            return cmdBuilder.build();
        }

        cmdBuilder.reset();
        String[] delimiters = cmdBuilder.getParams();
        if (delimiters.length == 0) {
            cmdBuilder.setArgument(data[1]);
            return cmdBuilder.build();
        }

        // multiple arguments. final delimiter will split two arguments.
        for (String delimiter : delimiters) {
            data = splitNextArg(data[1], delimiter);
            cmdBuilder.setArgument(data[0]);
        }
        cmdBuilder.setArgument(data[1]);
        return cmdBuilder.build();
    }

    private static String[] splitNextArg(String input, String delimiter) throws DukeParserException {
        String[] args = input.split("\\s" + delimiter + "\\s", 2);
        if (args.length < 2) {
            throw new DukeParserException("Missing arguments for \'" + delimiter + "\'");
        }
        return args;
    }

    /**
     * Parses the given date string into a date object.
     *
     * @param str the string to be parsed.
     * @return the date object representing the date.
     * @throws DukeParserException If the date is not formatted correctly.
     */
    public static Date parseDate(String str) throws DukeParserException {
        try {
            return Duke.DATE_FORMAT.parse(str);
        } catch (ParseException e) {
            throw new DukeParserException("Specify a date in the form \'"
                    + Duke.DATE_FORMAT.toPattern().toLowerCase() + "\'.");
        }
    }

    /**
     * Parses the given string into an integer.
     *
     * @param str the string to be parsed.
     * @return the integer value of the string.
     * @throws DukeParserException If the string given is not a valid integer.
     */
    public static int parseInt(String str) throws DukeParserException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new DukeParserException("That is not a valid number.");
        }
    }
}
