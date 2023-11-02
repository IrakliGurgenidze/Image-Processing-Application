package controller.command;

/**
 * This command quite the program..
 */
public class QuitCommand implements CommandController {

  @Override
  public String execute(String[] args) {
    return "quit";
  }

  @Override
  public String getUsage() {
    return "quit-- quits program";
  }
}
