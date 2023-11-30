package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for LinearColorTransformation's Sepia.
 */
public class SepiaCommandTest extends AbstractCommandTest {

    /**
     * A simple test for the sepia command.
     */
    @Test
    public void testSepiaCommand() {
        String[] loadBase = loadImage("manhattan-small.png", "man");
        imageController.runCommand(loadBase);
        assertEquals(1, imageModel.getSize());

        String[] funcCmd = imageController.parseCommand("sepia man mansepia");
        imageController.runCommand(funcCmd);
        assertEquals(imageModel.getSize(), 2);

        String[] loadExpected = loadImage("manhattan-small-sepia.png", "mans");
        imageController.runCommand(loadExpected);
        assertEquals(imageModel.getSize(), 3);

        Image expected = imageModel.getImage("mans");
        Image result = imageModel.getImage("mansepia");
        assertTrue(isEqual(expected, result));
    }
}
