package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for HorizontalFlipCommand.
 */
public class HorizontalFlipCommandTest extends AbstractCommandTest{

  /**
   * Tests a single horizontalFlip command.
   */
  @Test
  public void testHorizontalFlipCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("horizontal-flip man manhf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-horizontal.png", "msh");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msh");
    Image result = imageModel.getImage("manhf");
    assertTrue(isEqual(expected, result));
  }
}
