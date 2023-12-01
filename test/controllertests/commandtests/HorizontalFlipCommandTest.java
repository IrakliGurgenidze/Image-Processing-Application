package controllertests.commandtests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for HorizontalFlipCommand.
 */
public class HorizontalFlipCommandTest extends AbstractCommandTest {

  /**
   * Tests a single horizontalFlip command.
   */
  @Test
  public void testHorizontalFlipCommand() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("horizontal-flip man man-hf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-horizontal.png", "msh");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("msh");
    Image result = imageModel.getImage("man-hf");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test double horizontal-flip command.
   */
  @Test
  public void testDoubleHorizontalFlip() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //flip once
    String[] funcCmd = imageController.parseCommand("horizontal-flip man man-hf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    //flip again
    funcCmd = imageController.parseCommand("horizontal-flip man-hf man-hf-hf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);


    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-hf-hf");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test horizontal-flip tethered with vertical-flip command.
   */
  @Test
  public void testHorizontalVerticalFlip() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("horizontal-flip man man-hf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    funcCmd = imageController.parseCommand("vertical-flip man-hf man-hf-vf");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);

    String[] loadExpected = loadImage("manhattan-small-horizontal-vertical.png", "exp");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 4);

    Image expected = imageModel.getImage("exp");
    Image result = imageModel.getImage("man-hf-vf");
    assertTrue(isEqual(expected, result));
  }
}
