package controller.command;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements CommandController{
  private final Map<String, CommandController> commands;

  public HelpCommand(Map<String, CommandController> commands) {
    this.commands = commands;
  }

  @Override
  public void execute(String[] args) {
    for(CommandController x: commands.values()) {
      System.out.println(x.getUsage());
    }
  }

  @Override
  public String getUsage() {
    return "help-- lists all commands and their usage.";
  }
}
