package controller_tests.command_tests;

import org.junit.Test;

import java.io.File;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for RGBSplitCommand.
 */
public class RGBSplitCommandTest extends AbstractCommandTest {
  /**
   * Test rgb-split command.
   */
  @Test
  public void testRGBSplit() {
    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run rgb-split command
    String[] functionCommand = imageController.parseCommand("rgb-split man red green blue");
    imageController.runCommand(functionCommand);
    assertEquals(4, imageModel.getSize());

    //load expected image, red
    String[] loadRed = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-red.png red-expected");
    imageController.runCommand(loadRed);
    assertEquals(5, imageModel.getSize());

    //load expected image, green
    String[] loadGreen = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-green.png green-expected");
    imageController.runCommand(loadGreen);
    assertEquals(6, imageModel.getSize());

    //load expected image, blue
    String[] loadBlue = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-blue.png blue-expected");
    imageController.runCommand(loadBlue);
    assertEquals(7, imageModel.getSize());

    //test results
    Image expectedRed = imageModel.getImage("red-expected");
    Image resultRed = imageModel.getImage("red");
    assertTrue(isEqual(expectedRed, resultRed));

    Image expectedGreen = imageModel.getImage("green-expected");
    Image resultGreen = imageModel.getImage("green");
    assertTrue(isEqual(expectedGreen, resultGreen));

    Image expectedBlue = imageModel.getImage("blue-expected");
    Image resultBlue = imageModel.getImage("blue");
    assertTrue(isEqual(expectedBlue, resultBlue));
  }
}
