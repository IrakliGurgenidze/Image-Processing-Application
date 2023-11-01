package controller.command;

import model.Image;
import model.ImageStorageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the red component of an image.
 */
public class RedComponentCommand implements CommandController {

  //state of image database
  private final ImageStorageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RedComponentCommand(ImageStorageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: red-component image-name dest-image-name");
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        System.out.println("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image resultImage = getRedComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
      }
    }
  }

  //helper function to return the green component of an image
  private Image getRedComponent(Image source, String resultImageName) {
    SimpleImage redImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        redImage.setPixel(i, j, new Pixel(source.getPixel(i, j).getRed(), 0, 0));
      }
    }
    return redImage;
  }
}
