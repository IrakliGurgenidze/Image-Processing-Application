package controller.script.command;

import model.Image;
import model.StorageModel;


/**
 * This command compresses an image to facilitate more efficient storage.
 */
public class CompressCommand implements CommandController {
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the compress command.
   *
   * @param imageStorageModel state of image database
   */
  public CompressCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {

    //command takes 4 args: compress, percentage (an int), image-name, dest-image-name
    if (args.length != 4) {
      throw new IllegalArgumentException("Invalid input, looking for 4 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    //extract compression ratio
    int compression;
    try {
      compression = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid input. Compression value invalid. "
              + "Please enter an integer in the range of 0-100.");
    }

    if ((compression < 0) || (compression > 100)) {
      throw new IllegalArgumentException("Invalid input. Compression value invalid. "
              + "Please enter an integer in the range of 0-100.");
    }

    //extract image names and fetch source image
    String sourceImageName = args[2];
    String destImageName = args[3];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);

    //invalid image name provided, image does not exist or hasn't been loaded yet
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid input. Source image does not exist in database.");
    }

    //compute and store resulting image
    Image resultImage = sourceImage.compressImage(compression, destImageName);
    imageStorageModel.insertImage(resultImage);

    //return status
    return "Compression of " + sourceImageName + "completed. Saved as: " + destImageName;
  }

  @Override
  public String getUsage() {
    return "compress percentage image-name dest-image-name: compress image by given percentage to "
            + "create a new image, referred to henceforth as the destination image name. "
            + "Percentages between 0 and 100 are considered valid.";
  }


}
