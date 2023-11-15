package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for BlueComponentCommand.
 */
public class BlueComponentCommandTest extends AbstractCommandTest{
  /**
   * Test the command with the provided sample images.
   */
  @Test
  public void testBlueComponent(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("blue-component man manb");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-blue.png", "msb");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msb");
    Image result = imageModel.getImage("manb");
    assertTrue(isEqual(expected, result));
  }
}
