package controller.command;

/**
 * This interface acts as the controller for all the commands.
 */
public interface CommandController {
  /**
   * Executes the command.
   *
   * @param args String[], the arguments necessary to execute the given command
   */
  void execute(String[] args);
}
