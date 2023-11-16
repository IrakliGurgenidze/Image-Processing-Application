package controller_tests.command_tests;

import org.junit.Test;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the intensity-component command.
 */
public class IntensityComponentCommandTest extends AbstractCommandTest{

  /**
   * A simple test for the luma-component command.
   */
  @Test
  public void testIntensityComponentCommand(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("intensity-component man man-intensity");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-intensity");
    for (int x = 0; x < expected.getWidth(); x++) {
      for (int y = 0; y < expected.getHeight(); y++) {
        Pixel thisPixel = expected.getPixel(x, y);
        Pixel otherPixel = result.getPixel(x, y);

        //ensure individual channels equal value of base image
        assertEquals((int) thisPixel.getIntensity(), otherPixel.getRed());
        assertEquals((int) thisPixel.getIntensity(), otherPixel.getGreen());
        assertEquals((int) thisPixel.getIntensity(), otherPixel.getBlue());
      }
    }
  }
}
