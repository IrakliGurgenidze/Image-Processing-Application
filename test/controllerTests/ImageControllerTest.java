package controllerTests;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
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

          System.out.println(thisPixel.getRed() + " " + otherPixel.getRed());
          System.out.println(thisPixel.getGreen() + " " + otherPixel.getGreen());
          System.out.println(thisPixel.getBlue() + " " + otherPixel.getBlue());

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
    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //valid filepath
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small.png man");
    String response = imageController.runCommand(loadCommand);
    assertEquals("Image loaded.", response);
    assertEquals(1, imageModel.getSize());

    //invalid file path, non-enclosed space
    loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan small.png man");

    response = imageController.runCommand(loadCommand);
    assertEquals("File path and image name must be enclosed in \"\" if they contain a "
            + "space.", response);
    assertEquals(1, imageModel.getSize());


    //invalid file path, non-existent file
    loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattavvn-small.png man");
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

    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small.png man");
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

    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator+ "manhattan-small.png man");
    String[] functionCommand = imageController.parseCommand("red-component man man-red");
    String[] saveCommand = imageController.parseCommand("save " + workingDirectory
            + "result_images" + File.separator + "manhattan-small-red.png man-red");

    imageController.runCommand(loadCommand);
    imageController.runCommand(functionCommand);
    imageController.runCommand(saveCommand);
    assertEquals(2, imageModel.getSize());

    //test result
    String[] loadExpected = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small-red.png man-red-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());


    Image expected = imageModel.getImage("man-red-expected");
    Image result = imageModel.getImage("man-red");

    assertTrue(isEqual(expected, result));
  }

  /**
   * Test runCommand, blue-component.
   */
  @Test
  public void testBlueComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator+ "manhattan-small.png man");
    String[] functionCommand = imageController.parseCommand("blue-component man man-blue");
    String[] saveCommand = imageController.parseCommand("save " + workingDirectory
            + "result_images" + File.separator + "manhattan-small-blue.png man-blue");

    imageController.runCommand(loadCommand);
    imageController.runCommand(functionCommand);
    imageController.runCommand(saveCommand);
    assertEquals(2, imageModel.getSize());

    //test result
    String[] loadExpected = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small-blue.png man-blue-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());


    Image expected = imageModel.getImage("man-blue-expected");
    Image result = imageModel.getImage("man-blue");

    assertTrue(isEqual(expected, result));
  }

  /**
   * Test runCommand, green-component.
   */
  @Test
  public void testGreenComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator+ "manhattan-small.png man");
    String[] functionCommand = imageController.parseCommand("green-component man man-green");
    String[] saveCommand = imageController.parseCommand("save " + workingDirectory
            + "result_images" + File.separator + "manhattan-small-green.png man-green");

    imageController.runCommand(loadCommand);
    imageController.runCommand(functionCommand);
    imageController.runCommand(saveCommand);
    assertEquals(2, imageModel.getSize());

    //test result
    String[] loadExpected = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small-green.png man-green-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());


    Image expected = imageModel.getImage("man-green-expected");
    Image result = imageModel.getImage("man-green");

    assertTrue(isEqual(expected, result));
  }

  /**
   * Test runCommand, intensity-component.
   */
  @Test
  public void testIntensityComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator+ "manhattan-small.png man");
    String[] functionCommand = imageController.parseCommand("intensity-component man man-intensity");
    String[] saveCommand = imageController.parseCommand("save " + workingDirectory
            + "result_images" + File.separator + "manhattan-small-intensity-greyscale.png man-intensity");

    imageController.runCommand(loadCommand);
    imageController.runCommand(functionCommand);
    imageController.runCommand(saveCommand);
    assertEquals(2, imageModel.getSize());

    //test result
    String[] loadExpected = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small-intensity-greyscale.png man-intensity-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());


    Image expected = imageModel.getImage("man-intensity-expected");
    Image result = imageModel.getImage("man-intensity");

    assertTrue(isEqual(expected, result));
  }

  /**
   * Test runCommand, intensity-component.
   */
  @Test
  public void testLumaComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    String workingDirectory = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    //commands
    String[] loadCommand = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator+ "manhattan-small.png man");
    String[] functionCommand = imageController.parseCommand("luma-component man man-luma");
    String[] saveCommand = imageController.parseCommand("save " + workingDirectory
            + "result_images" + File.separator + "manhattan-small-luma.png man-luma");

    imageController.runCommand(loadCommand);
    imageController.runCommand(functionCommand);
    imageController.runCommand(saveCommand);
    assertEquals(2, imageModel.getSize());

    //test result
    String[] loadExpected = imageController.parseCommand("load " + workingDirectory
            + "sample_images" + File.separator + "manhattan-small-luma-greyscale.png man-luma-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());


    Image expected = imageModel.getImage("man-luma-expected");
    Image result = imageModel.getImage("man-luma");

    assertTrue(isEqual(expected, result));
  }



}
