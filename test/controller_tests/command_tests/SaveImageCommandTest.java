package controller_tests.command_tests;

import org.junit.Test;

import java.io.File;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for SaveImageCommand.
 */
public class SaveImageCommandTest extends AbstractCommandTest {
  /**
   * Test save command.
   */
  @Test
  public void testSave() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //save the image to results
    String[] saveBase = imageController.parseCommand("save "
            + workingDirectory
            + "result_images"
            + File.separator
            + "manhattan-small-saved.png man");
    imageController.runCommand(saveBase);
    assertEquals(1, imageModel.getSize());

    //load saved image and compare results
    String[] loadResult = imageController.parseCommand("load "
            + workingDirectory
            + "result_images"
            + File.separator
            + "manhattan-small-saved.png man-saved");
    imageController.runCommand(loadResult);
    assertEquals(2, imageModel.getSize()); //should have added an image

    //test results
    Image base = imageModel.getImage("man");
    Image savedBase = imageModel.getImage("man-saved");
    assertTrue(isEqual(base, savedBase));
    assertEquals(2, imageModel.getSize());
  }
}