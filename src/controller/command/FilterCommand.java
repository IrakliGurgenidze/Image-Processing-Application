package controller.command;

import controller.SplitUtil;
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

  //is split
  private boolean split;
  //split percentage
  private int splitPcnt;

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
    this.split = false;
    this.splitPcnt = 0;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    //check for split param
    checkSplit(args);

    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }

    Image filteredImage = sourceImage.applyFilter(filter, destImageName);

    if (split) {
      filteredImage = SplitUtil.splitImage(sourceImage, filteredImage, splitPcnt, destImageName);
    }

    imageStorageModel.insertImage(filteredImage);
    return "Completed " + filterName + " operation.";
  }

  @Override
  public String getUsage() {
    return filterName + "image-name dest-image-name: " + filterName + "the given image and\n "
            + "store the result in another image with the given name."
            + " split p: may be added as two additional parameters if split preview of operation is"
            + " desired. ";
  }

  //checks to see if the argument contains split parameter
  private void checkSplit(String[] args) throws IllegalArgumentException {
    if (args.length == 5 && args[3].equals("split")) {
      split = true;
      try {
        splitPcnt = Integer.parseInt(args[4]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Split percentage must be an integer between 0-100");
      }
      if (splitPcnt < 1 || splitPcnt > 99) {
        throw new IllegalArgumentException("Split percentage must be an integer between 0-100");
      }

    } else if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 or 5 arguments but only "
              + "found " + args.length + ". Correct usage: " + getUsage());
    }
  }
}