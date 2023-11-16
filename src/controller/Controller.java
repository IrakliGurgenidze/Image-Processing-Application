package controller;

import java.io.File;
import java.io.IOException;

/**
 * This interface outlines generic methods for the controller of an image editing application.
 */
public interface Controller {


  /**
   * This method "runs" the controller, giving it control over the program execution.
   *
   * @throws IOException on view display errors
   */
  void controllerGo() throws IOException;

  /**
   * This method "runs" the controller with a script filepath,
   * executing the script and then ending the program.
   *
   * @param scriptPath String, the of the script to be run
   * @throws IOException on view display errors
   */
  void controllerGo(String scriptPath) throws IOException;

  /**
   * Method to take in a single line of input, and split it into related keywords.
   *
   * @param command String, the command to be parsed
   * @return an array of strings containing tokenized command words from the input string
   */
  String[] parseCommand(String command);

  /**
   * Method to run the given command (passed from parseCommand).
   *
   * @param args String[], the command and arguments to be run
   * @return String, status of operation
   * @throws IllegalArgumentException if command cannot be run due to mis-input
   */
  String runCommand(String[] args) throws IllegalArgumentException;

  /**
   * Loads and runs the script commands in the given file.
   *
   * @param scriptFile File, the file to be loaded
   */
  String run(File scriptFile);


}
