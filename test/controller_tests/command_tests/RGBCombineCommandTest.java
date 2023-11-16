package controller_tests.command_tests;

import org.junit.Test;

import java.io.File;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for RGBCombineCommand.
 */
public class RGBCombineCommandTest extends AbstractCommandTest {
  /**
   * Test rgb-combine command.
   */
  @Test
  public void testRGBCombine() {

    //load base image
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run rgb-split command
    String[] functionCommand = imageController.parseCommand("rgb-split man red green blue");
    imageController.runCommand(functionCommand);
    assertEquals(4, imageModel.getSize());

    //run rgb-combine command
    functionCommand = imageController.parseCommand("rgb-combine combine-result red green blue");
    imageController.runCommand(functionCommand);
    assertEquals(5, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("combine-result");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test rgb-combine command, invalid image sizes.
   */
  @Test
  public void testRGBCombineInvalid() {

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

    //load different image
    String[] loadCropped = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-cropped.png crop");
    imageController.runCommand(loadCropped);
    assertEquals(5, imageModel.getSize());
  }
}
