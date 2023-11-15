package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for LumaComponentCommand.
 */
public class LumaComponentCommandTest extends AbstractCommandTest{

  /**
   * A simple test for the luma-component command.
   */
  @Test
  public void testLumaComponentCommand(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("luma-component man manl");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-luma-greyscale.png", "msl");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msl");
    Image result = imageModel.getImage("manl");
    assertTrue(isEqual(expected, result));
  }
}
