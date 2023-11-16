package controller_tests.command_tests;

import model.Image;
import model.Pixel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the SplitUtil class.
 */
public class SplitTest extends AbstractCommandTest {

    /**
     * Tests split with the filter commands.
     */
    @Test
    public void testFilterSplitParam() {
        String[] loadBase = loadImage("manhattan-small.png", "man");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        loadBase = loadImage("manhattan-small-blur.png", "manblur");
        imageController.runCommand(loadBase);
        assertEquals(2, imageModel.getSize());

        String[] funcCmd = imageController.parseCommand("blur man res split 50");
        imageController.runCommand(funcCmd);
        assertEquals(imageModel.getSize(), 3);

        Image resImage = imageModel.getImage("res");
        Image man = imageModel.getImage("man");
        Image manBlur = imageModel.getImage("manblur");

        int height = resImage.getHeight();
        int width = resImage.getWidth();
        int pct = (int) (width * (50 / 100.0));

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = resImage.getPixel(x, y);
                if (x < pct) {
                    assertEquals(currPixel.getRed(), manBlur.getPixel(x, y).getRed());
                    assertEquals(currPixel.getGreen(), manBlur.getPixel(x, y).getGreen());
                    assertEquals(currPixel.getBlue(), manBlur.getPixel(x, y).getBlue());
                } else {
                    assertEquals(currPixel.getRed(), man.getPixel(x, y).getRed());
                    assertEquals(currPixel.getGreen(), man.getPixel(x, y).getGreen());
                    assertEquals(currPixel.getBlue(), man.getPixel(x, y).getBlue());
                }
            }
        }
    }
}
