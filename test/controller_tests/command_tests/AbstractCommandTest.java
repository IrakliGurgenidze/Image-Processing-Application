package controller_tests.command_tests;

import org.junit.Test;

import java.io.File;

import controller.Controller;
import controller.ImageController;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;

/**
 * Abstract class with helper methods for tests of commands.
 */
public abstract class AbstractCommandTest {

  /**
   * Test the command with the provided sample images.
   */
  @Test
  public abstract void testCommand();

  ImageStorageModel imageModel = new ImageStorageModel();
  Controller imageController = new ImageController(imageModel);
  String workingDirectory = setWd();


  //helper method to determine the equivalency of two images
  protected boolean isEqual(Image thisImg, Image otherImg) {
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
  protected String setWd() {
    return System.getProperty("user.dir")
            + File.separator
            + "test"
            + File.separator;
  }

  //helper method to load base image
  protected String[] loadImage(String fileName, String destImageName){
    return imageController.parseCommand("load "
            + workingDirectory
            + "sample_images"
            + File.separator
            + fileName
            + " "
            + destImageName);
  }


}
