package controllertests.commandtests;

import org.junit.Test;

import java.io.File;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for LoadCommand.
 */
public class LoadCommandTest extends AbstractCommandTest {

  /**
   * Tests the load command.
   */
  @Test
  public void loadTest() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    String response = imageController.runCommand(loadBase);
    assertEquals("Image loaded.", response);
    assertEquals(1, imageModel.getSize());

    //file path with improper space
    loadBase = loadImage("manhattan small.png", "man");
    response = imageController.runCommand(loadBase);
    assertEquals("File path and image name must be enclosed in \"\" if they contain a "
            + "space.", response);
    assertEquals(1, imageModel.getSize()); //model should not have changed

    //invalid file path, non-existent file
    final String[] loadInvalid = loadImage("non-existent.jpg", "ne");
    assertThrows(IllegalArgumentException.class, () -> imageController.runCommand(loadInvalid));
    assertEquals(1, imageModel.getSize());
  }

  @Test
  public void loadPPMTest() {

    String[] loadExpected = loadImage("manhattan-small.png", "man");
    String response = imageController.runCommand(loadExpected);
    assertEquals("Image loaded.", response);
    assertEquals(1, imageModel.getSize());

    //save the image to results
    String[] saveBase = imageController.parseCommand("save "
            + workingDirectory
            + "result_images"
            + File.separator
            + "manhattan-small-saved.ppm man");
    imageController.runCommand(saveBase);
    assertEquals(1, imageModel.getSize());

    //load saved image and compare results
    String[] loadResult = imageController.parseCommand("load "
            + workingDirectory
            + "result_images"
            + File.separator
            + "manhattan-small-saved.ppm manPPM");
    imageController.runCommand(loadResult);
    assertEquals(2, imageModel.getSize()); //should have added an image

    //assert equal
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("manPPM");
    assertTrue(isEqual(expected, result));

    //invalid file path, non-existent file
    final String[] loadInvalid = loadImage("non-existent.ppm", "ne");
    assertThrows(IllegalArgumentException.class, () -> imageController.runCommand(loadInvalid));
    assertEquals(2, imageModel.getSize());
  }

}
