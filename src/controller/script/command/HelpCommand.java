package controller.script.command;

import java.util.Map;

/**
 * This command lists all the stored commands and their usages.
 */
public class HelpCommand implements CommandController {
    private final Map<String, CommandController> commands;

    /**
     * The constructor to initialize the help command.
     *
     * @param commands the state of the storage model
     */
    public HelpCommand(Map<String, CommandController> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder helpMessage = new StringBuilder();
        //for each command, get its usage
        for (CommandController x : commands.values()) {
            helpMessage.append(x.getUsage());
            helpMessage.append("\n\n");
        }
        return helpMessage.toString();
    }

    @Override
    public String getUsage() {
        return "help-- lists all commands and their usage.";
    }
}
