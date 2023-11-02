package controller.command;

import model.ImageStorageModel;
import model.Image;
import model.Pixel;
import model.SimpleImage;

/**
 * This command brightens or darkens an image based on given increment.
 */
public class BrightenCommand implements CommandController {
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public BrightenCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 4) {
      return "Invalid input. Usage: " + getUsage();
    } else {
      try {
        int increment = Integer.parseInt(args[1]);
        String sourceImageName = args[2];
        String destImageName = args[3];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);
        if (sourceImage == null) {
          return "Invalid request. Image with name + " + sourceImageName
                  + "not found.";
        } else {
          Image destImage = brighten(increment, sourceImage, destImageName);
          imageStorageModel.insertImage(destImage);
        }
      } catch (NumberFormatException e) {
        return "Invalid increment.";
      }
      return "Completed brighten operation.";
    }
  }

  @Override
  public String getUsage() {
    return "brighten increment image-name dest-image-name: brighten the image by the given \n" +
            "increment to create a new image, referred to henceforth by the given destination\n " +
            "name. The increment may be positive (brightening) or negative (darkening).";
  }

  //helper function to compute and return a brightened image
  private Image brighten(int increment, Image image, String destImageName) {
    int height = image.getHeight();
    int width = image.getWidth();

    Image brightenedImage = new SimpleImage(width, height, destImageName);

    int red;
    int green;
    int blue;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = image.getPixel(i, j);

        red = currPixel.getRed() + increment;
        green = currPixel.getGreen() + increment;
        blue = currPixel.getBlue() + increment;

        brightenedImage.setPixel(i, j, new Pixel(red, green, blue));
      }
    }

    return brightenedImage;
  }


}
