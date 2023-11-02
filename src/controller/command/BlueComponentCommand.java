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
      return "Invalid input. Usage: " + getUsage();
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        return "Invalid request. Image with name + " + sourceImageName
                + "not found.";
      } else {
        Image resultImage = getBlueComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed blue-component operation.";
      }
    }
  }

  @Override
  public String getUsage() {
    return "blue-component image-name dest-image-name: Create an image with the\n " +
            "blue-component of the image with the given name, and refer to it henceforth in\n" +
            "the program by the given destination name.";
  }

  //helper method to return the blue component of an image
  private Image getBlueComponent(Image source, String resultImageName) {
    Image blueImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        blueImage.setPixel(j, i, new Pixel(0, 0,
                source.getPixel(j, i).getBlue()));
      }
    }
    return blueImage;
  }
}
