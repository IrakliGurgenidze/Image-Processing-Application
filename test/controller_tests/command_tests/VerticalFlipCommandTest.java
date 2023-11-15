package controller_tests.command_tests;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for VerticalFlipCommand.
 */
public class VerticalFlipCommandTest extends AbstractCommandTest{
  @Override
  public void testCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("vertical-flip man manvf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-vertical.png", "msv");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msv");
    Image result = imageModel.getImage("manvf");
    assertTrue(isEqual(expected, result));
  }
}
