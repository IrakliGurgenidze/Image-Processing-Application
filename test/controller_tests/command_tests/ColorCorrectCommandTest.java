package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for ColorCorrectCommand.
 */
public class ColorCorrectCommandTest extends AbstractCommandTest{
@Override
  public void testCommand(){
    String[] loadBase = loadImage("galaxy-tinge.png", "gt");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("color-correct gt gtc");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("galaxy-corrected.png", "gtce");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("gtce");
    Image result = imageModel.getImage("gtc");
    assertTrue(isEqual(expected, result));
  }
}
