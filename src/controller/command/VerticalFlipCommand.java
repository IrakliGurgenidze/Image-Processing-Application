package controller.command;

import model.Image;
import model.StorageModel;


/**
 * This command computes and stores the vertical flip of an image.
 */
public class VerticalFlipCommand implements CommandController {

  //model state
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of the image database
   */
  public VerticalFlipCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }
    String sourceImageName = args[1];
    String resultImageName = args[2];

    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Image with name " + sourceImageName + " not found.");
    }

    Image resultImage = sourceImage.getVerticalFlip(resultImageName);
    imageStorageModel.insertImage(resultImage);
    return "Completed vertical-flip operation.";
  }

  @Override
  public String getUsage() {
    return "vertical-flip image-name dest-image-name: Flip an image vertically\n "
            + "to create a new image, referred to henceforth by the given destination name.";
  }
}
