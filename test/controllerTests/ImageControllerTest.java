package controllerTests;

import org.junit.Test;

import java.io.File;

import controller.Controller;
import controller.ImageController;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests various controller methods.
 */
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

  //helper method to set working directory to the "resources" folder
  private String setWd() {
    return System.getProperty("user.dir")
            + File.separator
            + "test"
            + File.separator;
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
   * Test load command.
   */
  @Test
  public void testLoad() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load an image and verify it has been stored correctly
    String[] loadCommand = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    String response = imageController.runCommand(loadCommand);
    assertEquals("Image loaded.", response);
    assertEquals(1, imageModel.getSize());

    //invalid file path, non-enclosed space
    loadCommand = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan small.png man");
    response = imageController.runCommand(loadCommand);
    assertEquals("File path and image name must be enclosed in \"\" if they contain a "
            + "space.", response);
    assertEquals(1, imageModel.getSize()); //model should not have changed


    //invalid file path, non-existent file
    loadCommand = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "non-existent.png ne");
    response = imageController.runCommand(loadCommand);
    assertEquals("Unable to locate image at specified path.", response);
    assertEquals(1, imageModel.getSize());
  }


  /**
   * Test save command.
   */
  @Test
  public void testSave() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load an image into the database
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
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


  /**
   * Test red-component command.
   */
  @Test
  public void testRedComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run red-component command
    String[] functionCommand = imageController.parseCommand("red-component man man-red");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-red.png man-red-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-red-expected");
    Image result = imageModel.getImage("man-red");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test green-component command.
   */
  @Test
  public void testGreenComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run green-component command
    String[] functionCommand = imageController.parseCommand("green-component man man-green");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-green.png man-green-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-green-expected");
    Image result = imageModel.getImage("man-green");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test blue-component command.
   */
  @Test
  public void testBlueComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run blue-component command
    String[] functionCommand = imageController.parseCommand("blue-component man man-blue");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-blue.png man-blue-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-blue-expected");
    Image result = imageModel.getImage("man-blue");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test value-component command.
   */
  @Test
  public void testValueComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run value-component command
    String[] functionCommand = imageController.parseCommand("value-component man man-value");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-value");
    for (int i = 0; i < expected.getHeight(); i++) {
      for (int j = 0; j < expected.getWidth(); j++) {
        Pixel thisPixel = expected.getPixel(j, i);
        Pixel otherPixel = result.getPixel(j, i);

        //ensure individual channels equal value of base image
        assertEquals(thisPixel.getValue(), otherPixel.getRed());
        assertEquals(thisPixel.getValue(), otherPixel.getGreen());
        assertEquals(thisPixel.getValue(), otherPixel.getBlue());
      }
    }
  }


  /**
   * Test intensity-component command.
   */
  @Test
  public void testIntensityComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run red-component command
    String[] functionCommand = imageController.parseCommand("intensity-component man "
            + "man-intensity");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-intensity-greyscale.png man-intensity-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-intensity");
    for (int i = 0; i < expected.getHeight(); i++) {
      for (int j = 0; j < expected.getWidth(); j++) {
        Pixel thisPixel = expected.getPixel(j, i);
        Pixel otherPixel = result.getPixel(j, i);

        //ensure individual channels equal value of base image
        assertEquals((int) thisPixel.getIntensity(), otherPixel.getRed());
        assertEquals((int) thisPixel.getIntensity(), otherPixel.getGreen());
        assertEquals((int) thisPixel.getIntensity(), otherPixel.getBlue());
      }
    }
  }


  /**
   * Test luma-component command.
   */
  @Test
  public void testLumaComponent() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run luma-component command
    String[] functionCommand = imageController.parseCommand("luma-component man man-luma");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-luma-greyscale.png man-luma-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-luma-expected");
    Image result = imageModel.getImage("man-luma");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test horizontal-flip command.
   */
  @Test
  public void testHorizontalFlip() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run horizontal-flip command
    String[] functionCommand = imageController.parseCommand("horizontal-flip man man-horizontal");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-horizontal.png man-horizontal-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-horizontal-expected");
    Image result = imageModel.getImage("man-horizontal");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test double horizontal-flip command.
   */
  @Test
  public void testDoubleHorizontalFlip() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run horizontal-flip command
    String[] functionCommand = imageController.parseCommand("horizontal-flip man man-horizontal");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //run horizontal-flip command again
    functionCommand = imageController.parseCommand("horizontal-flip man-horizontal man-hor-double");
    imageController.runCommand(functionCommand);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-hor-double");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Test vertical-flip command.
   */
  @Test
  public void testVerticalFlip() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run vertical flip command
    String[] functionCommand = imageController.parseCommand("vertical-flip man man-vertical");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-vertical.png man-vertical-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-vertical-expected");
    Image result = imageModel.getImage("man-vertical");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test double vertical-flip command.
   */
  @Test
  public void testDoubleVerticalFlip() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run vertical-flip command
    String[] functionCommand = imageController.parseCommand("vertical-flip man man-vertical");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //run vertical-flip command again
    functionCommand = imageController.parseCommand("vertical-flip man-vertical man-ver-double");
    imageController.runCommand(functionCommand);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man");
    Image result = imageModel.getImage("man-ver-double");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test vertical-flip tethered with horizontal-flip command.
   */
  @Test
  public void testHorizontalVerticalFlip() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run vertical-flip command
    String[] functionCommand = imageController.parseCommand("vertical-flip man man-vertical");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //run horizontal-flip command
    functionCommand = imageController.parseCommand("horizontal-flip man-vertical man-vert-hor");
    imageController.runCommand(functionCommand);
    assertEquals(3, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-vertical-horizontal.png man-vert-hor-expected");
    imageController.runCommand(loadExpected);
    assertEquals(4, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-vert-hor-expected");
    Image result = imageModel.getImage("man-vert-hor");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test horizontal-flip tethered with vertical-flip command.
   */
  @Test
  public void testVerticalHorizontalFlip() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run vertical-flip command
    String[] functionCommand = imageController.parseCommand("horizontal-flip man man-horizontal");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //run horizontal-flip command
    functionCommand = imageController.parseCommand("vertical-flip man-horizontal man-hor-vert");
    imageController.runCommand(functionCommand);
    assertEquals(3, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-horizontal-vertical.png man-hor-vert-expected");
    imageController.runCommand(loadExpected);
    assertEquals(4, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-hor-vert-expected");
    Image result = imageModel.getImage("man-hor-vert");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test brighten command, positive increment.
   */
  @Test
  public void testBrightenPositive() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run brighten command
    String[] functionCommand = imageController.parseCommand("brighten 50 man man-brighter");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-brighter-by-50.png man-brighter-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-brighter-expected");
    Image result = imageModel.getImage("man-brighter");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test brighten command, negative increment.
   */
  @Test
  public void testBrightenNegative() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run brighten command
    String[] functionCommand = imageController.parseCommand("brighten -50 man man-darker");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small-darker-by-50.png man-darker-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-darker-expected");
    Image result = imageModel.getImage("man-darker");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test brighten command, hitting upper pixel channel limit.
   */
  @Test
  public void testBrightenPositiveLimit() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run brighten command
    String[] functionCommand = imageController.parseCommand("brighten 300 man man-brighter");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //test results
    Image result = imageModel.getImage("man-brighter");
    for (int i = 0; i < result.getHeight(); i++) {
      for (int j = 0; j < result.getWidth(); j++) {
        Pixel thisPixel = result.getPixel(j, i);

        //ensure individual channels equal value of base image
        assertEquals(255, thisPixel.getRed());
        assertEquals(255, thisPixel.getGreen());
        assertEquals(255, thisPixel.getBlue());
      }
    }
  }


  /**
   * Test brighten command, hitting lower pixel channel limit.
   */
  @Test
  public void testBrightenLowerLimit() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run brighten command
    String[] functionCommand = imageController.parseCommand("brighten -300 man man-darker");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //test results
    Image result = imageModel.getImage("man-darker");
    for (int i = 0; i < result.getHeight(); i++) {
      for (int j = 0; j < result.getWidth(); j++) {
        Pixel thisPixel = result.getPixel(j, i);

        //ensure individual channels equal value of base image
        assertEquals(0, thisPixel.getRed());
        assertEquals(0, thisPixel.getGreen());
        assertEquals(0, thisPixel.getBlue());
      }
    }
  }


  /**
   * Test rgb-split command.
   */
  @Test
  public void testRGBSplit() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

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


  /**
   * Test rgb-combine command.
   */
  @Test
  public void testRGBCombine() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

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
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

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


    //run rgb-combine command
    functionCommand = imageController.parseCommand("rgb-combine combine-result red green crop");
    assertEquals("Unable to complete rgb-combine operation. Please ensure images exist, "
                    + "and have the same dimensions.", imageController.runCommand(functionCommand));
  }


  /**
   * Test blur command.
   */
  @Test
  public void testBlur() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run blur command
    String[] functionCommand = imageController.parseCommand("blur man man-blur");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-blurred.png man-blur-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-blur-expected");
    Image result = imageModel.getImage("man-blur");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test sharpen command.
   */
  @Test
  public void testSharpen() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run sharpen command
    String[] functionCommand = imageController.parseCommand("sharpen man man-sharpen");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-sharpen.png man-sharpen-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-sharpen-expected");
    Image result = imageModel.getImage("man-sharpen");
    assertTrue(isEqual(expected, result));
  }


  /**
   * Test sepia command.
   */
  @Test
  public void testSepia() {
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);

    //set file path to resources
    String workingDirectory = setWd();

    //load base image
    String[] loadBase = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + "manhattan-small.png man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    //run sepia command
    String[] functionCommand = imageController.parseCommand("sepia man man-sepia");
    imageController.runCommand(functionCommand);
    assertEquals(2, imageModel.getSize());

    //load expected image
    String[] loadExpected = imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator + "manhattan-small-sepia.png man-sepia-expected");
    imageController.runCommand(loadExpected);
    assertEquals(3, imageModel.getSize());

    //test results
    Image expected = imageModel.getImage("man-sepia-expected");
    Image result = imageModel.getImage("man-sepia");
    assertTrue(isEqual(expected, result));
  }

  @Test
  public void testRun(){
    ImageStorageModel imageModel = new ImageStorageModel();
    Controller imageController = new ImageController(imageModel);
    String workingDirectory = setWd();
    File script = new File(workingDirectory
            + File.separator
            + "controllerTests"
            + File.separator
            + "testing_script.txt");
    imageController.run(script);
    assertEquals(10, imageModel.getSize());
  }


}
