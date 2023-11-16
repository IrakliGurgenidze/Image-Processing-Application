package controller.command;

import model.Image;
import model.StorageModel;
import model.utilities.LinearColorTransformation;


/**
 * This command computes and stores a linear color transformation of an image.
 */
public class LinearColorTransformationCommand implements CommandController {

  //state of image database
  private final StorageModel imageStorageModel;
  //name of transformation to be applied
  private final String transformationName;
  private final double[][] transformation;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel  state of image database
   * @param transformationName name of transformation to be applied
   */
  public LinearColorTransformationCommand(StorageModel imageStorageModel,
                                          String transformationName) {
    LinearColorTransformation lct = new LinearColorTransformation();
    this.imageStorageModel = imageStorageModel;
    this.transformationName = transformationName;
    this.transformation = lct.getLinearTransformation(transformationName);
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);

    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }

    Image destImage = sourceImage.applyLinearColorTransformation(transformation, destImageName);
    imageStorageModel.insertImage(destImage);
    return "Completed " + transformationName + " operation.";
  }

  @Override
  public String getUsage() {
    return "sepia image-name dest-image-name: produce a sepia-toned version of\n "
            + "the given image and store the result in another image with the given name.";
  }
}
