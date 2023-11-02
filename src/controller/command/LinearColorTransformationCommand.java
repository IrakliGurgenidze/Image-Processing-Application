package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;
import model.utilities.LinearColorTransformation;

/**
 * This command computes and stores a linear color transformation of an image.
 */
public class LinearColorTransformationCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  //name of transformation to be applied
  private final String transformationName;

  //database of available LinearColorTransformations
  private final LinearColorTransformation transformations;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel  state of image database
   * @param transformationName name of transformation to be applied
   */
  public LinearColorTransformationCommand(ImageStorageModel imageStorageModel,
                                          String transformationName) {
    this.imageStorageModel = imageStorageModel;
    this.transformationName = transformationName;
    this.transformations = new LinearColorTransformation();
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      return "Invalid input. Usage: " + getUsage();
    } else {
      String sourceImageName = args[1];
      String destImageName = args[2];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        return "Invalid request. Image with name + " + sourceImageName
                + "not found.";
      } else {
        Image destImage = applyLinearTransformation(destImageName, sourceImage);
        imageStorageModel.insertImage(destImage);
        return "Completed color transformation.";
      }
    }
  }

  @Override
  public String getUsage() {
    return "sepia image-name dest-image-name: produce a sepia-toned version of\n "
            + "the given image and store the result in another image with the given name.";
  }

  //helper method to compute and return the linear color transformation of an image
  private Image applyLinearTransformation(String destImageName, Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    double[][] transformation = transformations.getLinearTransformation(transformationName);
    Image transformedImage = new SimpleImage(width, height, destImageName);

    int red;
    int green;
    int blue;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = image.getPixel(x, y);

        red = (int) (transformation[0][0] * currPixel.getRed()
                + transformation[0][1] * currPixel.getGreen()
                + transformation[0][2] * currPixel.getBlue());

        green = (int) (transformation[1][0] * currPixel.getRed()
                + transformation[1][1] * currPixel.getGreen()
                + transformation[1][2] * currPixel.getBlue());

        blue = (int) (transformation[2][0] * currPixel.getRed()
                + transformation[2][1] * currPixel.getGreen()
                + transformation[2][2] * currPixel.getBlue());

        transformedImage.setPixel(x, y, new Pixel(red, green, blue));
      }
    }

    return transformedImage;
  }
}
