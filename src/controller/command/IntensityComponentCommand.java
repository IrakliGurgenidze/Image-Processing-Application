package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the intensity component of an image.
 */
public class IntensityComponentCommand implements CommandController {

  //model state
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of the image database
   */
  public IntensityComponentCommand(ImageStorageModel imageStorageModel) {
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
        Image resultImage = getIntensityComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed intensity-component operation.";
      }
    }
  }

  @Override
  public String getUsage() {
    return "intensity-component image-name dest-image-name: Create an image with the\n " +
            "intensity-component of the image with the given name, and refer to it henceforth in\n" +
            "the program by the given destination name.";
  }

  //helper method to return the luma component of an image
  private Image getIntensityComponent(Image source, String resultImageName) {
    Image intensityImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        Pixel currPixel = source.getPixel(j, i);
        intensityImage.setPixel(j, i, new Pixel((int) currPixel.getIntensity()));
      }
    }
    return intensityImage;
  }
}
