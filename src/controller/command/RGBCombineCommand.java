package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores an image that combines the RGB channels of 3 separate images.
 */
public class RGBCombineCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RGBCombineCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 5) {
      return "Invalid input. Usage: " + getUsage();
    } else {
      String combinedImageName = args[1];
      String redImageName = args[2];
      String greenImageName = args[3];
      String blueImageName = args[4];

      Image combinedImage = combine(redImageName, greenImageName, blueImageName,
              combinedImageName);

      if (combinedImage != null) {
        imageStorageModel.insertImage(combinedImage);
        return "Completed rgb-combine operation.";
      }

      return "Unable to complete rgb-combine operation.";
    }
  }

  @Override
  public String getUsage() {
    return "rgb-combine image-name red-image green-image blue-image: Combine the\n " +
            "three greyscale images into a single image that gets its red, green and\n " +
            "blue components from the three images respectively.";
  }

  //helper method to combine RGB channels from 3 images
  private Image combine(String red, String green, String blue, String combined) {
    Image redImage = imageStorageModel.getImage(red);
    if (redImage == null) {
      System.out.println("Invalid request. Image with name " + red + " not found.");
      return null;
    }
    Image greenImage = imageStorageModel.getImage(green);
    if (greenImage == null) {
      System.out.println("Invalid request. Image with name " + green + " not found.");
      return null;
    }
    Image blueImage = imageStorageModel.getImage(blue);
    if (blueImage == null) {
      System.out.println("Invalid request. Image with name " + blue + " not found.");
      return null;
    }
    Image combinedImage = new SimpleImage(redImage.getWidth(), redImage.getHeight(), combined);

    for (int i = 0; i < redImage.getHeight(); i++) {
      for (int j = 0; j < redImage.getWidth(); j++) {
        Pixel currRedPixel = redImage.getPixel(j, i);
        Pixel currGreenPixel = greenImage.getPixel(j, i);
        Pixel currBluePixel = blueImage.getPixel(j, i);

        int redComp = currRedPixel.getRed();
        int greenComp = currGreenPixel.getGreen();
        int blueComp = currBluePixel.getBlue();

        Pixel combinedPixel = new Pixel(redComp, greenComp, blueComp);

        combinedImage.setPixel(j, i, combinedPixel);
      }
    }
    return combinedImage;
  }
}
