package controller.command;

import model.Image;
import model.StorageModel;
import model.utilities.Filter;


/**
 * This command applies a filter to an image and stores the result.
 */
public class FilterCommand implements CommandController {
  private final StorageModel imageStorageModel;
  private final String filterName;
  private final double[][] filter;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   * @param filterName        name of filter to be applied to image
   */
  public FilterCommand(StorageModel imageStorageModel, String filterName) {
    Filter filters = new Filter();
    this.imageStorageModel = imageStorageModel;
    this.filterName = filterName;
    this.filter = filters.getFilter(filterName);
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
    Image filteredImage = sourceImage.applyFilter(filter, destImageName);
    imageStorageModel.insertImage(filteredImage);
    return "Completed " + filterName + " operation.";
  }

  @Override
  public String getUsage() {
    return filterName + "image-name dest-image-name: " + filterName + "the given image and\n "
            + "store the result in another image with the given name.";
  }
}