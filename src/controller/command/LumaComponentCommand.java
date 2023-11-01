package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the value component of an image.
 */
public class LumaComponentCommand implements CommandController {

  //model state
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of the image database
   */
  public LumaComponentCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: luna-component image-name dest-image-name");
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        System.out.println("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image resultImage = getLumaComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
      }
    }
  }

  //helper method to return the luma component of an image
  private Image getLumaComponent(Image source, String resultImageName) {
    SimpleImage lumaImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        Pixel currPixel = source.getPixel(i, j);

        lumaImage.setPixel(i, j, new Pixel((int) currPixel.getLuma()));
      }
    }
    return lumaImage;
  }
}
