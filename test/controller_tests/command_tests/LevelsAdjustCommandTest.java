package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for LevelsAdjustCommand.
 */
public class LevelsAdjustCommandTest extends AbstractCommandTest {
  /**
   * Test the command with the provided sample images.
   */
  @Test
  public void testCommand(){
    String[] loadBase = loadImage("galaxy.png", "gt");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("levels-adjust 20 100 255 gt gta");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("galaxy-adjusted.png", "gtae");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    imageController.runCommand(new String[]{"save", workingDirectory + "result_images/galaxy-adjusted-test.png", "gta"});
    Image expected = imageModel.getImage("gtae");
    Image result = imageModel.getImage("gta");
    assertTrue(isEqual(expected, result));
  }
}
