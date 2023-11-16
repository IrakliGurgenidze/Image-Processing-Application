package controller.command;

import model.StorageModel;


/**
 * This command saves an image from the database to a local location.
 */
public class SaveImageCommand implements CommandController {

  //state of image database
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public SaveImageCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    if (args.length != 3) {
      if (args.length > 3 && (!args[1].startsWith("\"") || (!args[1].endsWith("\""))
              || ((!args[2].startsWith("\"") || !args[2].endsWith("\""))))) {
        throw new IllegalArgumentException("File path and image name must be enclosed in \"\" if " +
                "they contain a space.");
      }
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    String imagePath = args[1];
    String imageName = args[2];
    try {
      imageStorageModel.saveImage(imagePath, imageName);
      return "Image saved to specified path.";
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public String getUsage() {
    return "save image-path image-name: Save the image with the given name to the\n "
            + "specified path which should include the name of the file.";
  }
}
