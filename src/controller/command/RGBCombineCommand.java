package controller.command;

import model.Image;
import model.ImageStorageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores an image that combines the RGB channels of 3 separate images.
 */
public class RGBCombineCommand implements CommandController {

  //state of image database
  private final ImageStorageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RGBCombineCommand(ImageStorageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

    @Override
    public void execute(String[] args) {
        String combinedImageName = args[1];
        String redImageName = args[2];
        String greenImageName = args[3];
        String blueImageName = args[4];

      Image combinedImage = combine(redImageName, greenImageName, blueImageName,
              combinedImageName);

      if (combinedImage != null) {
        imageStorageModel.insertImage(combinedImage);
      }
    }
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

    for (int y = 0; y < redImage.getHeight(); y++) {
      for (int x = 0; x < redImage.getWidth(); x++) {
        Pixel currRedPixel = redImage.getPixel(x, y);
        Pixel currGreenPixel = greenImage.getPixel(x, y);
        Pixel currBluePixel = blueImage.getPixel(x, y);

        int redComp = currRedPixel.getRed();
        int greenComp = currGreenPixel.getGreen();
        int blueComp = currBluePixel.getBlue();

        Pixel combinedPixel = new Pixel(redComp, greenComp, blueComp);

        combinedImage.setPixel(x, y, combinedPixel);
      }
    }
    return combinedImage;
  }
}
