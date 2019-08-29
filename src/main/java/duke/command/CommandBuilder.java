package duke.command;

import java.util.ArrayList;
import java.util.List;

class CommandBuilder {
    private final Command command;
    private List<String> arguments;

    private CommandBuilder(Command command) {
        this.command = command;
        reset();
    }

    String[] getParams() {
        return command.getParams();
    }

    void setArgument(String arg) {
        arguments.add(arg);
    }

    static CommandBuilder getCommandBuilder(Command command) {
        return new CommandBuilder(command);
    }

    void reset() {
        arguments = new ArrayList<>();
    }

    Command build() {
        return command.generate(arguments);
    }
}
