package controller_tests.command_tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * A JUnit test class for LoadCommand.
 */
public class LoadCommandTest extends AbstractCommandTest{
  /**
   * Tests the load command.
   */
  @Test
  public void loadTest(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    String response = imageController.runCommand(loadBase);
    assertEquals("Image loaded.", response);
    assertEquals(1, imageModel.getSize());

    //file path with improper space
    loadBase = loadImage("manhattan small.png", "man");
    response = imageController.runCommand(loadBase);
    assertEquals("File path and image name must be enclosed in \"\" if they contain a "
            + "space.", response);
    assertEquals(1, imageModel.getSize()); //model should not have changed

    //invalid file path, non-existent file
    final String [] loadInvalid = loadImage("non-existent.jpg", "ne");
    assertThrows(IllegalArgumentException.class, () -> imageController.runCommand(loadInvalid));
    assertEquals(1, imageModel.getSize());
  }
}
