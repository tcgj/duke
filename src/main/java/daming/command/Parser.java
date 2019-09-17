package daming.command;

import daming.Daming;
import daming.exception.DamingParserException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a parser for string inputs.
 *
 * @author Terence Chong Guang Jun
 */
public class Parser {
    /**
     * Parses the entire given input string, and returns the respective command.
     * The input arguments are stored in the command object.
     *
     * @param input the string to be parsed.
     * @return the command that represents the user input.
     * @throws DamingParserException If the command provided is invalid or the required date is not specified.
     */
    public static Command parse(CommandCenter commandCenter, String input) throws DamingParserException {
        String[] data = input.trim().split("\\s+", 2);
        Function<List<String>, ? extends Command> command = commandCenter.getCommand(data[0]);
        List<String> argsList = new ArrayList<>();
        if (command == null) {
            throw new DamingParserException("I don't understand what that means.");
        }
        if (data.length != 2) {
            return command.apply(argsList);
        }

        String[] delimiters = commandCenter.getParameters(data[0]);
        for (String delimiter : delimiters) {
            data = splitNextArg(data[1], delimiter);
            argsList.add(data[0]);
        }
        argsList.add(data[1]);
        return command.apply(argsList);
    }

    private static String[] splitNextArg(String input, String delimiter) throws DamingParserException {
        String[] args = input.split(delimiter, 2);
        if (args.length < 2) {
            throw new DamingParserException("Missing argument(s) after \'" + args[0] + "\'");
        }
        return args;
    }

    /**
     * Parses the given date string into a date object.
     *
     * @param str the string to be parsed.
     * @return the date object representing the date.
     * @throws DamingParserException If the date is not formatted correctly.
     */
    public static Date parseDate(String str) throws DamingParserException {
        try {
            return Daming.DATE_FORMAT.parse(str);
        } catch (ParseException e) {
            throw new DamingParserException("Specify a date in the form \'"
                    + Daming.DATE_FORMAT.toPattern().toLowerCase() + "\'.");
        }
    }

    /**
     * Parses the given string into an integer.
     *
     * @param str the string to be parsed.
     * @return the integer value of the string.
     * @throws DamingParserException If the string given is not a valid integer.
     */
    public static int parseInt(String str) throws DamingParserException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new DamingParserException("That is not a valid number.");
        }
    }
}
