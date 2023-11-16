package controller.command;

import model.Image;
import model.StorageModel;


/**
 * This command flips an image horizontally.
 */
public class HorizontalFlipCommand implements CommandController {

  //state of image database
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public HorizontalFlipCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }
    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name + " + sourceImageName
              + "not found.");
    }
    Image destImage = sourceImage.getHorizontalFlip(destImageName);
    imageStorageModel.insertImage(destImage);
    return "Completed horizontal-flip operation.";
  }

  @Override
  public String getUsage() {
    return "horizontal-flip image-name dest-image-name: Flip an image horizontally\n "
            + "to create a new image, referred to henceforth by the given destination name.";
  }
}
