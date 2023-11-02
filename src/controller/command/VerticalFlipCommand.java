package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the vertical flip of an image.
 */
public class VerticalFlipCommand implements CommandController {

  //model state
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of the image database
   */
  public VerticalFlipCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      return "Invalid input. Usage: " + getUsage();
    }
    String sourceImageName = args[1];
    String resultImageName = args[2];

    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      return "Image with name " + sourceImageName + " not found.";
    } else {
      Image resultImage = getVerticalFlip(sourceImage, resultImageName);
      imageStorageModel.insertImage(resultImage);
      return "Completed vertical-flip operation.";
    }
  }

  @Override
  public String getUsage() {
    return "vertical-flip image-name dest-image-name: Flip an image vertically\n " +
            "to create a new image, referred to henceforth by the given destination name.";
  }

  //helper method to return the vertica flip of an image
  private Image getVerticalFlip(Image source, String resultImageName) {
    SimpleImage verticalFlipImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        Pixel currPixel = source.getPixel(j, i);
        int flippedI = source.getHeight() - i - 1;

        verticalFlipImage.setPixel(flippedI, j, currPixel);
      }
    }
    return verticalFlipImage;
  }
}