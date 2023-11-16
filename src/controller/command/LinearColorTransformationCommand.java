package controller.command;

import controller.SplitUtil;
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

  //is split
  private boolean split;
  //split percentage
  private int splitPcnt;

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
    this.split = false;
    this.splitPcnt = 0;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    checkSplit(args);

    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);

    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }

    Image destImage = sourceImage.applyLinearColorTransformation(transformation, destImageName);
    if(split){
      destImage = SplitUtil.splitImage(sourceImage, destImage, splitPcnt, destImageName);
    }
    imageStorageModel.insertImage(destImage);
    return "Completed " + transformationName + " operation.";
  }

  @Override
  public String getUsage() {
    return "sepia image-name dest-image-name: produce a sepia-toned version of\n "
            + "the given image and store the result in another image with the given name." +
            "split p: may be added as two additional parameters if split preview of operation is desired.";
  }

  //checks to see if the argument contains split parameter
  private void checkSplit(String[] args) throws IllegalArgumentException{
    if(args.length == 5 && args[3].equals("split")){
      split = true;
      try {
        splitPcnt = Integer.parseInt(args[4]);
      }catch(NumberFormatException e){
        throw new IllegalArgumentException("Split percentage must be an integer between 0-100");
      }
      if(splitPcnt < 1 || splitPcnt > 99){
        throw new IllegalArgumentException("Split percentage must be an integer between 0-100");
      }

    }else if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 or 5 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }
  }
}
