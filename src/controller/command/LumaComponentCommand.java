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
  public String execute(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
       throw new IllegalArgumentException("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image resultImage = getLumaComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed luna-component operation.";
      }
    }
  }

  @Override
  public String getUsage() {
    return "luma-component image-name dest-image-name: Create an image with the\n "
            + "luma-component of the image with the given name, and refer to it henceforth in\n"
            + "the program by the given destination name.";
  }

  //helper method to return the luma component of an image
  private Image getLumaComponent(Image source, String resultImageName) {
    SimpleImage lumaImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        Pixel currPixel = source.getPixel(j, i);
        int luma = (int) currPixel.getLuma();
        lumaImage.setPixel(j, i, new Pixel(luma));
      }
    }

    return lumaImage;
  }
}
