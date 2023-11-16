package controller.command;

import model.Image;
import model.Pixel;
import model.SimpleImage;
import model.StorageModel;


/**
 * This command adjusts the color levels of an image.
 */
public class LevelsAdjustCommand implements CommandController {
  private final StorageModel imageStorageModel;

  /**
   * This is the constructor to initialize the command.
   *
   * @param imageStorageModel state of image database
   */
  public LevelsAdjustCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    if (args.length != 6) {
      throw new IllegalArgumentException("Invalid input, looking for 6 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }
    int b;
    int m;
    int w;

    try {
      b = Integer.parseInt(args[1]);
      m = Integer.parseInt(args[2]);
      w = Integer.parseInt(args[3]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("b,m,w must be integers between 0-255 ascending in " +
              "listed order");
    }

    if (w < m || m < b) {
      throw new IllegalArgumentException("b,m,w must be integers between 0-255 ascending in " +
              "listed order");
    }

    if (b < 0 || w > 255) {
      throw new IllegalArgumentException("b,m,w must be integers between 0-255 ascending in " +
              "listed order");
    }

    String sourceImageName = args[4];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }
    String destImageName = args[5];
    Image adjustedImage = sourceImage.adjustLevels(destImageName, b, m, w);
    imageStorageModel.insertImage(adjustedImage);
    return "Levels adjustment operation successful.";
  }

  @Override
  public String getUsage() {
    return "levels-adjust b m w image-name dest-image-name: where b, m and w are the three " +
            "relevant black, mid and white values respectively. These values should be ascending " +
            "in that order, and should be within 0 and 255 for this command to work correctly.";
  }
}
