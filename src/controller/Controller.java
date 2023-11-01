package controller;

import java.io.File;
import java.io.IOException;

/**
 * This interface outlines generic methods for the controller of an image editing application.
 */
public interface Controller {

  /**
   * Method to take in a single line of input, and split it into related keywords.
   *
   * @param command String, the command to be parsed
   * @return an array of strings containing tokenized command words from the input string
   */
  String[] parseCommand(String command);

  /**
   * Method to run the given command (passed from parseCommand)
   *
   * @param args String[], the command and arguments to be run
   */
  void runCommand(String[] args) throws IOException;

  /**
   * Loads and runs the script commands in the given file
   *
   * @param scriptFile File, the file to be loaded
   */
  void run(File scriptFile);


}
