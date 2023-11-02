package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command flips an image horizontally.
 */
public class HorizontalFlipCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public HorizontalFlipCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      return "Invalid input. Usage: " + getUsage();
    } else {
      String sourceImageName = args[1];
      String destImageName = args[2];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      Image destImage = horizontalFlip(sourceImage, destImageName);
      imageStorageModel.insertImage(destImage);
      return "Completed horizontal-flip operation.";
    }
  }

  @Override
  public String getUsage() {
    return "horizontal-flip image-name dest-image-name: Flip an image horizontally\n " +
            "to create a new image, referred to henceforth by the given destination name.";
  }

  //helper method to return the horizontally flipped image
  private Image horizontalFlip(Image source, String resultImageName) {
    int height = source.getHeight();
    int width = source.getWidth();

    Image newImage = new SimpleImage(width, height, resultImageName);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = source.getPixel(j, i);

        //horizontal flip -> new x coordinate
        int flippedX = width - j - 1;

        newImage.setPixel(flippedX, i, currPixel);
      }
    }
    return newImage;
  }
}
