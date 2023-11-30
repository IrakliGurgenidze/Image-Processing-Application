package view.script;

import java.io.IOException;

/**
 * Represents the view of the program.
 */
public interface ScriptView {

    /**
     * Displays the status fetched from the controller based on last command.
     */
    void displayStatus(String status) throws IOException;

    /**
     * Retrieves user input and sends it to controller to be parsed.
     *
     * @return String, the line of user input
     */
    String getInput();
}
