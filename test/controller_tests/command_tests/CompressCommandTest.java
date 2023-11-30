package controller_tests.command_tests;

import org.junit.Test;

import java.io.File;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for CompressCommand.
 */
public class CompressCommandTest extends AbstractCommandTest {

    /**
     * Test that program flags invalid compression ratio.
     */
    @Test
    public void testInvalidCompressionRatio() {
        String[] loadBase = loadImage("manhattan-small.png", "man");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        String[] negativeRatioCmd = imageController.parseCommand("compress -1 man manC");
        assertThrows(IllegalArgumentException.class,
                () -> imageController.runCommand(negativeRatioCmd));

        String[] greaterThan100RatioCmd = imageController.parseCommand("compress 101 man manC");
        assertThrows(IllegalArgumentException.class,
                () -> imageController.runCommand(greaterThan100RatioCmd));

        String[] decimalRatioCmd = imageController.parseCommand("compress 50.3 man manC");
        assertThrows(IllegalArgumentException.class, () -> imageController.runCommand(decimalRatioCmd));
    }

    /**
     * Test the compression with a ratio of 0, should result in an identical image.
     */
    @Test
    public void testLosslessCompression() {
        String[] loadBase = loadImage("manhattan-small.png", "man");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        String[] funcCmd = imageController.parseCommand("compress 0 man manC");
        imageController.runCommand(funcCmd);
        assertEquals(imageModel.getSize(), 2);

        Image expected = imageModel.getImage("man");
        Image result = imageModel.getImage("manC");
        assertTrue(isEqual(expected, result));
    }

    /**
     * Test the compression with a ratio of 90, should result in a smaller file size.
     */
    @Test
    public void testPartialCompression() {
        String[] loadBase = loadImage("manhattan-small.png", "man");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        String[] funcCmd = imageController.parseCommand("compress 90 man manC");
        imageController.runCommand(funcCmd);
        assertEquals(imageModel.getSize(), 2);

        String[] saveCompressed = imageController.parseCommand("save "
                + workingDirectory
                + "result_images"
                + File.separator
                + "manhattan-small-compressed-90.png manC");
        imageController.runCommand(saveCompressed);

        //get file sizes
        String basePath = workingDirectory
                + "sample_images"
                + File.separator
                + "manhattan-small.png";

        String compressedPath = workingDirectory
                + "result_images"
                + File.separator
                + "manhattan-small-compressed-90.png";

        File baseImg = new File(basePath);
        File compressedImg = new File(compressedPath);

        //compressed file size should be smaller than original
        assertTrue(baseImg.length() > compressedImg.length());
    }

    /**
     * Test the compression with a ratio of 100, result should be all black.
     */
    @Test
    public void testFullCompression() {
        String[] loadBase = loadImage("manhattan-small.png", "man");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        String[] funcCmd = imageController.parseCommand("compress 100 man manC");
        imageController.runCommand(funcCmd);
        assertEquals(imageModel.getSize(), 2);

        Image result = imageModel.getImage("manC");


        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                Pixel thisPixel = result.getPixel(x, y);

                //ensure individual channels equal value of base image
                assertEquals(thisPixel.getRed(), 0);
                assertEquals(thisPixel.getGreen(), 0);
                assertEquals(thisPixel.getBlue(), 0);
            }
        }
    }


}
