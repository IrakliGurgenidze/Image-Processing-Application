package controller.command;

import model.Image;
import model.Pixel;
import model.SimpleImage;
import model.StorageModel;


/**
 * This command color corrects an image to align all histogram peaks.
 */
public class ColorCorrectCommand implements CommandController {
  private final StorageModel imageStorageModel;

  /**
   * This is the constructor to initialize the command.
   *
   * @param imageStorageModel state of image database
   */
  public ColorCorrectCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }

    Image colorCorrected = sourceImage.colorCorrectImage(destImageName);
    imageStorageModel.insertImage(colorCorrected);
    return "Color correction operation successful.";
  }

  @Override
  public String getUsage() {
    return null;
  }
}
