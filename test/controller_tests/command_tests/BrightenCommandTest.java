package controller_tests.command_tests;

import org.junit.Test;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for BrightenCommand.
 */
public class BrightenCommandTest extends AbstractCommandTest {

  /**
   * Tests the positive increment.
   */
  @Test
  public void testPositiveBrighten() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten 50 man manbrighter");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-brighter-by-50.png", "manbr");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("manbr");
    Image result = imageModel.getImage("manbrighter");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Tests the negative increment.
   */
  @Test
  public void testNegativeBrighten() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten -50 man mandarker");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-darker-by-50.png", "manbr");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("manbr");
    Image result = imageModel.getImage("mandarker");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test brighten command, hitting upper pixel channel limit.
   */
  @Test
  public void testBrightenUpperLimit() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten 255 man man-brighter");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    //test results
    Image result = imageModel.getImage("man-brighter");
    for (int x = 0; x < result.getWidth(); x++) {
      for (int y = 0; y < result.getHeight(); y++) {
        Pixel thisPixel = result.getPixel(x, y);

        //ensure individual channels equal value of base image
        assertEquals(255, thisPixel.getRed());
        assertEquals(255, thisPixel.getGreen());
        assertEquals(255, thisPixel.getBlue());
      }
    }
  }

  /**
   * Test brighten command, hitting lower pixel channel limit.
   */
  @Test
  public void testBrightenLowerLimit() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten -255 man man-darker");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    //test results
    Image result = imageModel.getImage("man-darker");
    for (int x = 0; x < result.getWidth(); x++) {
      for (int y = 0; y < result.getHeight(); y++) {
        Pixel thisPixel = result.getPixel(x, y);

        //ensure individual channels equal value of base image
        assertEquals(0, thisPixel.getRed());
        assertEquals(0, thisPixel.getGreen());
        assertEquals(0, thisPixel.getBlue());
      }
    }
  }
}
