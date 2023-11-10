package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

import java.io.FileNotFoundException;

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
      throw new IllegalArgumentException("Invalid input, looking for 4 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    else {
      try {
        int increment = Integer.parseInt(args[1]);
        String sourceImageName = args[2];
        String destImageName = args[3];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);
        if (sourceImage == null) {
          throw new IllegalArgumentException("Invalid request. Image with name + " + sourceImageName
                  + "not found.");
        } else {
          Image destImage = brighten(increment, sourceImage, destImageName);
          imageStorageModel.insertImage(destImage);
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Brightening increment invalid. "
                + "Please enter an integer.");
      }
      return "Completed brighten operation.";
    }
  }

  @Override
  public String getUsage() {
    return "brighten increment image-name dest-image-name: brighten the image by the given \n"
            + "increment to create a new image, referred to henceforth by the given destination\n "
            + "name. The increment may be positive (brightening) or negative (darkening).";
  }

  //helper function to compute and return a brightened image
  private Image brighten(int increment, Image source, String resultImageName) {
    int height = source.getHeight();
    int width = source.getWidth();

    Image brightenedImage = new SimpleImage(width, height, resultImageName);

    int red;
    int green;
    int blue;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = source.getPixel(x, y);

        red = currPixel.getRed() + increment;
        green = currPixel.getGreen() + increment;
        blue = currPixel.getBlue() + increment;

        brightenedImage.setPixel(x, y, new Pixel(red, green, blue));
      }
    }

    return brightenedImage;
  }


}
