package controller.command;

import java.io.IOException;

import model.Image;
import model.ImageStorageStorageModel;
import model.utilities.ImageUtil;

/**
 * This command loads an image and stores it in the database.
 */
public class LoadImageCommand implements CommandController {

  //state of image database
  private final ImageStorageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public LoadImageCommand(ImageStorageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: load image-path image-name");
    } else {
      String imagePath = args[1];
      String imageName = args[2];

      Image image = loadImage(imagePath, imageName);
      if (image != null) {
        imageStorageModel.insertImage(image);
      }
    }
  }

  //helper method to read an image from a file
  private Image loadImage(String fileName, String imageName) {
    try {
      if (fileName.split("\\.")[1].equals("ppm")) {
        return ImageUtil.readColorPPM(fileName, imageName);
      } else {
        return ImageUtil.readColor(fileName, imageName);
      }
    } catch (IOException e) {
      System.out.println("File " + fileName + "does not exist.");
    }

    return null;
  }
}