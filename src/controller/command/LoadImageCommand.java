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
  public String execute(String[] args) throws IllegalArgumentException {
    if (args.length != 3) {
      if (args.length > 3 && (!args[1].startsWith("\"") || (!args[1].endsWith("\""))
              || ((!args[2].startsWith("\"") || !args[2].endsWith("\""))))) {
        return "File path and image name must be enclosed in \"\" if they contain a space.";
      }
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    String imagePath = args[1];
    String imageName = args[2];
    try {
      imageStorageModel.loadImage(imagePath, imageName);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return "Image loaded.";
  }

  @Override
  public String getUsage() {
    return "load image-path image-name: Load an image from the specified path and refer\n "
            + "to it henceforth in the program by the given image name.";
  }
}