package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for Filter's Sharpen.
 */
public class SharpenCommandTest extends AbstractCommandTest{

  /**
   * A simple test for the sharpen-command.
   */
  @Test
  public void testSharpenCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("sharpen man mansh");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-sharpen.png", "msb");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msb");
    Image result = imageModel.getImage("mansh");
    assertTrue(isEqual(expected, result));
  }
}
