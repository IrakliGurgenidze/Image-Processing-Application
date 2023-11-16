package controller.command;

import model.StorageModel;


/**
 * This command loads an image and stores it in the database.
 */
public class LoadImageCommand implements CommandController {

  //state of image database
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public LoadImageCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      if (args.length > 3 && (!args[1].startsWith("\"") || (!args[1].endsWith("\""))
              || ((!args[2].startsWith("\"") || !args[2].endsWith("\""))))) {
        return "File path and image name must be enclosed in \"\" if they contain a space.";
      }
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    } else {
      String imagePath = args[1];
      String imageName = args[2];
      Image image = loadImage(imagePath, imageName);
      if (image == null) {
        throw new IllegalArgumentException("Unable to locate image at specified path.");
      }

      imageStorageModel.insertImage(image);
      return "Image loaded.";
    }
  }

  @Override
  public String getUsage() {
    return "load image-path image-name: Load an image from the specified path and refer\n "
            + "to it henceforth in the program by the given image name.";
  }

  //helper method to read an image from a file
  private Image loadImage(String fileName, String imageName) {
    try {
      if (fileName.split("\\.")[1].equals("ppm")) {
        return ImageUtil.readColorPPM(fileName, imageName);
      } else {
        return ImageUtil.readColor(fileName, imageName);
      }
    } catch (Exception e) {
      return null;
    }
  }


}