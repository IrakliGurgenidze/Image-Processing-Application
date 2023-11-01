package controller.command;

import java.io.IOException;

import model.Image;
import model.ImageStorageModel;
import model.utilities.ImageUtil;

/**
 * This command loads an image and stores it in the database.
 */
public class LoadImageCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public LoadImageCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: " + getUsage());
    } else {
      String imagePath = args[1];
      String imageName = args[2];

      Image image = loadImage(imagePath, imageName);
      if (image != null) {
        imageStorageModel.insertImage(image);
      }
    }
  }

  @Override
  public String getUsage() {
    return "load image-path image-name: Load an image from the specified path and refer\n " +
            "to it henceforth in the program by the given image name.";
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