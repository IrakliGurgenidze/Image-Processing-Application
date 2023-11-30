package controller_tests.command_tests;

import org.junit.Test;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for the histogram command.
 */
public class HistogramCommandTest extends AbstractCommandTest {

    /**
     * A simple test for the histogram command.
     */
    @Test
    public void testHistogramCommand() {
        String[] loadBase = loadImage("galaxy.png", "gal");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        String[] funcCmd = imageController.parseCommand("histogram gal galH");
        imageController.runCommand(funcCmd);
        assertEquals(imageModel.getSize(), 2);

        String[] loadExpected = loadImage("galaxy-histogram.png", "expected");
        imageController.runCommand(loadExpected);
        assertEquals(3, imageModel.getSize());

        //test results
        Image expected = imageModel.getImage("expected");
        Image result = imageModel.getImage("galH");
        for (int x = 0; x < expected.getWidth(); x++) {
            for (int y = 0; y < expected.getHeight(); y++) {
                Pixel thisPixel = expected.getPixel(x, y);
                Pixel otherPixel = result.getPixel(x, y);

                if (isPureRGB(thisPixel)) {
                    assertEquals(thisPixel.getRed(), otherPixel.getRed());
                    assertEquals(thisPixel.getGreen(), otherPixel.getGreen());
                    assertEquals(thisPixel.getBlue(), otherPixel.getBlue());
                }
            }
        }
    }


}
