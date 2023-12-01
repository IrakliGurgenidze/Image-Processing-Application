package controllertests.commandtests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for GreenComponentCommand.
 */
public class GreenComponentCommandTest extends AbstractCommandTest {

  /**
   * Test green-component command.
   */
  @Test
  public void testGreenComponentCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("green-component man mang");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-green.png", "msg");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msg");
    Image result = imageModel.getImage("mang");
    assertTrue(isEqual(expected, result));
  }
}
