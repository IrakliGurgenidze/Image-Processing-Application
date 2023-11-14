package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the blue component of an image.
 */
public class BlueComponentCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public BlueComponentCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                + " not found.");
      }
        Image resultImage = getBlueComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed blue-component operation.";
  }

  @Override
  public String getUsage() {
    return "blue-component image-name dest-image-name: Create an image with the\n "
            + "blue-component of the image with the given name, and refer to it henceforth in\n"
            + "the program by the given destination name.";
  }

  //helper method to return the blue component of an image
  private Image getBlueComponent(Image source, String resultImageName) {
    Image blueImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int x = 0; x < source.getWidth(); x++) {
      for (int y = 0; y < source.getHeight(); y++) {
        blueImage.setPixel(x, y, new Pixel(0, 0,
                source.getPixel(x, y).getBlue()));
      }

    }
    return blueImage;
  }
}
