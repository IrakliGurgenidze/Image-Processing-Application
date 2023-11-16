package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for VerticalFlipCommand.
 */
public class VerticalFlipCommandTest extends AbstractCommandTest {

  /**
   * A simple test for the verticalFlip command.
   */
  @Test
  public void testVerticalFlipCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("vertical-flip man man-vf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-vertical.png", "msv");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msv");
    Image result = imageModel.getImage("man-vf");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test double vertical-flip command.
   */
  @Test
  public void testDoubleVerticalFlip() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //flip once
    String[] funcCmd = imageController.parseCommand("vertical-flip man man-vf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    //flip again
    funcCmd = imageController.parseCommand("vertical-flip man-vf man-vf-vf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-vf-vf");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test vertical-flip tethered with horizontal-flip command.
   */
  @Test
  public void testVerticalHorizontalFlip() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("vertical-flip man man-vf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    funcCmd = imageController.parseCommand("horizontal-flip man-vf man-vf-hf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);

    String[] loadExpected = loadImage("manhattan-small-horizontal-vertical.png", "exp");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 4);

    Image expected = imageModel.getImage("exp");
    Image result = imageModel.getImage("man-vf-hf");
    assertTrue(isEqual(expected, result));
  }
}
