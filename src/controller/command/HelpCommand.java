package controller.command;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements CommandController{
  private final Map<String, CommandController> commands;

  public HelpCommand(Map<String, CommandController> commands) {
    this.commands = commands;
  }

  @Override
  public String execute(String[] args) {
    StringBuilder helpMessage = new StringBuilder();
    //for each command, get its usage
    for(CommandController x: commands.values()) {
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
