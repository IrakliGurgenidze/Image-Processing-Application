package controllerTests;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.Controller;
import controller.ImageController;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;
import model.StorageModel;

public class ImageControllerTest {


  //helper method to determine the equivalency of two images
  private boolean isEqual(Image thisImg, Image otherImg) {
    if (thisImg.getHeight() != otherImg.getHeight()) {
      return false;
    }

    if (thisImg.getWidth() != otherImg.getWidth()) {
      return false;
    }

    //check contents of each pixel
    for (int i = 0; i < thisImg.getHeight(); i++) {
      for (int j = 0; j < thisImg.getWidth(); j++) {
        Pixel thisPixel = thisImg.getPixel(j, i);
        Pixel otherPixel = otherImg.getPixel(j, i);
        if (thisPixel.getRed() != otherPixel.getRed()
                || thisPixel.getGreen() != otherPixel.getGreen()
                || thisPixel.getBlue() != otherPixel.getBlue()) {

          return false;
        }
      }
    }

    return true;
  }


  /**
   * Test parseCommand.
   */
  @Test
  public void testParseCommand() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);


    //parse basic command and check results
    String[] parsedCommand = imageController.parseCommand("load filepath.ppm man");
    assertEquals(3, parsedCommand.length);
    assertEquals("load", parsedCommand[0]);
    assertEquals("filepath.ppm", parsedCommand[1]);
    assertEquals("man", parsedCommand[2]);

    //a more complex example
    parsedCommand = imageController.parseCommand("load \"filepa th.ppm\" man");
    assertEquals(3, parsedCommand.length);
    assertEquals("load", parsedCommand[0]);
    assertEquals("filepa th.ppm", parsedCommand[1]);
    assertEquals("man", parsedCommand[2]);

    //spaces in both path and name
    parsedCommand = imageController.parseCommand("load \"filepa th.ppm\" \"m an\"");
    assertEquals(3, parsedCommand.length);
    assertEquals("load", parsedCommand[0]);
    assertEquals("filepa th.ppm", parsedCommand[1]);
    assertEquals("m an", parsedCommand[2]);

    //should also handle - in command entry
    parsedCommand = imageController.parseCommand("green-component \"filepa th.ppm\" \"m an\"");
    assertEquals(3, parsedCommand.length);
    assertEquals("green-component", parsedCommand[0]);
    assertEquals("filepa th.ppm", parsedCommand[1]);
    assertEquals("m an", parsedCommand[2]);
  }

  /**
   * Test load.
   */
  @Test
  public void testLoad() {

    //valid file path
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);
    String workingDirectory = System.getProperty("user.dir") + "\\resources\\";

    //valid filepath
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "\\sample_images\\manhattan-small.png man");
    String response = imageController.runCommand(loadCommand);
    assertEquals("Image loaded.", response);
    assertEquals(1, imageModel.getSize());

    //invalid file path, non-enclosed space
    loadCommand = imageController.parseCommand("load " + workingDirectory
            + "\\sample_images\\manhattan small.png man");

    response = imageController.runCommand(loadCommand);
    assertEquals("File path and image name must be enclosed in \"\" if they contain a "
            + "space.", response);
    assertEquals(1, imageModel.getSize());


    //invalid file path, non-existent file
    loadCommand = imageController.parseCommand("load " + workingDirectory
            + "\\sample_images\\manhattavvn-small.png man");
    response = imageController.runCommand(loadCommand);
    assertEquals("Unable to locate image at specified path.", response);
    assertEquals(1, imageModel.getSize());

  }

  /**
   * Test save.
   */
  @Test
  public void testSave() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    String workingDirectory = System.getProperty("user.dir") + "\\resources\\";

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "\\sample_images\\manhattan-small.png man");
    imageController.runCommand(loadCommand);

    assertEquals(1, imageModel.getSize());
  }

  /**
   * Test runCommand, red-component.
   */
  @Test
  public void testRedComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    String workingDirectory = System.getProperty("user.dir") + "\\resources\\";

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "\\sample_images\\manhattan-small.png man");
    String[] functionCommand = imageController.parseCommand("red-component man man-red");
    String[] saveCommand = imageController.parseCommand("save " + workingDirectory
            + "\\result_images\\manhattan-small-red.png man-red");

    imageController.runCommand(loadCommand);
    imageController.runCommand(functionCommand);
    imageController.runCommand(saveCommand);
    assertEquals(2, imageModel.getSize());

    //test result
    String[] loadExpected = imageController.parseCommand("load " + workingDirectory
            + "\\sample_images\\manhattan-small-red.png man-red-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());


    Image expected = imageModel.getImage("man-red-expected");
    Image result = imageModel.getImage("man-red");

    assertTrue(isEqual(expected, result));
  }




}
