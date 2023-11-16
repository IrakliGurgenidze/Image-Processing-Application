package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.StorageModel;

/**
 * This command creates a histogram representing the RGB channels of an image.
 */
public class HistogramCommand implements CommandController {

  private final StorageModel imageStorageModel;

  /**
   * The constructor to initialize the histogram command.
   *
   * @param imageStorageModel the state of the storage model
   */
  public HistogramCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found"
              + args.length + ". Correct usage: " + getUsage());
    }
    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }

    Image destImage = getHistogram(sourceImage, destImageName);
    imageStorageModel.insertImage(destImage);

    return "Completed histogram creation. File saved as: " + destImageName;
  }

  @Override
  public String getUsage() {
    return "histogram image-name dest-image-name: creates an histogram for the RGB intensities of the given" +
            "image. The image is saved in the database under the destination image name.";
  }
}
