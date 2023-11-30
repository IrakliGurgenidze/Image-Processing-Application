package controller.script.command;

import model.Image;
import model.StorageModel;


/**
 * This command brightens or darkens an image based on given increment.
 */
public class BrightenCommand implements CommandController {
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public BrightenCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {

    //expecting 4 arguments
    if (args.length != 4) {
      throw new IllegalArgumentException("Invalid input, looking for 4 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    //extract arguments
    try {
      int increment = Integer.parseInt(args[1]);
      String sourceImageName = args[2];
      String destImageName = args[3];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);

      //image by given name hasn't been loaded yet
      if (sourceImage == null) {
        throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                + " not found.");
      }

      Image resultImage = sourceImage.brighten(increment, destImageName);
      imageStorageModel.insertImage(resultImage);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Brightening increment invalid. "
              + "Please enter an integer.");
    }

    return "Completed brighten operation.";
  }

  @Override
  public String getUsage() {
    return "brighten increment image-name dest-image-name: brighten the image by the given \n"
            + "increment to create a new image, referred to henceforth by the given destination\n "
            + "name. The increment may be positive (brightening) or negative (darkening).";
  }
}
