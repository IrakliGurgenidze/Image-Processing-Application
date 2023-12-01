package controllertests.commandtests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for RedComponentCommand.
 */
public class RedComponentCommandTest extends AbstractCommandTest {

  /**
   * A simple test for the red-component command.
   */
  @Test
  public void testRedComponentCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("red-component man manr");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-red.png", "msr");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msr");
    Image result = imageModel.getImage("manr");
    assertTrue(isEqual(expected, result));
  }
}
