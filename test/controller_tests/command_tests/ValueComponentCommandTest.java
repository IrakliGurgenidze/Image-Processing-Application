package controller_tests.command_tests;

import org.junit.Test;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the value-component command.
 */
public class ValueComponentCommandTest extends AbstractCommandTest{

  /**
   * A simple test for the luma-component command.
   */
  @Test
  public void testValueComponentCommand(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("value-component man man-value");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-value");
    for (int x = 0; x < expected.getWidth(); x++) {
      for (int y = 0; y < expected.getHeight(); y++) {
        Pixel thisPixel = expected.getPixel(x, y);
        Pixel otherPixel = result.getPixel(x, y);

        //ensure individual channels equal value of base image
        assertEquals(thisPixel.getValue(), otherPixel.getRed());
        assertEquals(thisPixel.getValue(), otherPixel.getGreen());
        assertEquals(thisPixel.getValue(), otherPixel.getBlue());
      }
    }
  }
}
