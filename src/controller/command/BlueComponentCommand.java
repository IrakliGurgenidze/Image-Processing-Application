package controller.command;

import model.Image;
import model.ImageStorageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the blue component of an image.
 */
public class BlueComponentCommand implements CommandController {

  //state of image database
  private final ImageStorageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public BlueComponentCommand(ImageStorageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: blue-component image-name dest-image-name");
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        System.out.println("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image resultImage = getBlueComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
      }
    }
  }

  //helper method to return the blue component of an image
  private Image getBlueComponent(Image source, String resultImageName) {
    Image blueImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        blueImage.setPixel(i, j, new Pixel(0, 0,
                source.getPixel(i, j).getBlue()));
      }
    }
    return blueImage;
  }
}
