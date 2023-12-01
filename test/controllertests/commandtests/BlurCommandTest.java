package controllertests.commandtests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for Filter's Blur.
 */
public class BlurCommandTest extends AbstractCommandTest {

  /**
   * Test blur command.
   */
  @Test
  public void testBlurCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("blur man manblur");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-blur.png", "msb");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msb");
    Image result = imageModel.getImage("manblur");
    assertTrue(isEqual(expected, result));
  }
}
