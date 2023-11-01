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
  public LinearColorTransformationCommand(ImageStorageModel imageStorageModel, String transformationName) {
    this.imageStorageModel = imageStorageModel;
    this.transformationName = transformationName;
    this.transformations = new LinearColorTransformation();
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: "
              + transformationName + " image-name dest-image-name");
    } else {
      String sourceImageName = args[1];
      String destImageName = args[2];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        System.out.println("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image destImage = applyLinearTransformation(destImageName, sourceImage);
        imageStorageModel.insertImage(destImage);
      }
    }
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

    for (int j = 0; j < width; j++) {
      for (int i = 0; i < height; i++) {
        Pixel currPixel = image.getPixel(i, j);

        red = (int) (transformation[0][0] * currPixel.getRed()
                + transformation[0][1] * currPixel.getGreen()
                + transformation[0][2] * currPixel.getBlue());

        green = (int) (transformation[1][0] * currPixel.getRed()
                + transformation[1][1] * currPixel.getGreen()
                + transformation[1][2] * currPixel.getBlue());

        blue = (int) (transformation[2][0] * currPixel.getRed()
                + transformation[2][1] * currPixel.getGreen()
                + transformation[2][2] * currPixel.getBlue());

        transformedImage.setPixel(j, i, new Pixel(red, green, blue));
      }
    }

    return transformedImage;
  }
}
