package controller.command;

import model.Image;
import model.ImageStorageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the vertical flip of an image.
 */
public class VerticalFlipCommand implements CommandController {

  //model state
  private final ImageStorageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of the image database
   */
  public VerticalFlipCommand(ImageStorageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: vertical-flip image-name dest-image-name");
    }
    String sourceImageName = args[1];
    String resultImageName = args[2];

    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      System.out.println("Image with name " + sourceImageName + " not found.");
    } else {
      Image resultImage = getVerticalFlip(sourceImage, resultImageName);
      imageStorageModel.insertImage(resultImage);
    }
  }

  //helper method to return the vertica flip of an image
  private Image getVerticalFlip(Image source, String resultImageName) {
    SimpleImage verticalFlipImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        Pixel currPixel = source.getPixel(i, j);
        int flippedI = source.getHeight() - i - 1;

        verticalFlipImage.setPixel(flippedI, j, currPixel);
      }
    }
    return verticalFlipImage;
  }
}
