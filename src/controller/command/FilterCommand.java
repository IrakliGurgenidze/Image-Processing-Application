package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;
import model.utilities.Filter;

/**
 * This command applies a filter to an image and stores the result.
 */
public class FilterCommand implements CommandController {
  private final String filterName;
  private final ImageStorageModel imageStorageModel;
  private final Filter filters;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   * @param filterName        name of filter to be applied to image
   */
  public FilterCommand(ImageStorageModel imageStorageModel, String filterName) {
    this.imageStorageModel = imageStorageModel;
    this.filterName = filterName;
    filters = new Filter();
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    } else {
      String sourceImageName = args[1];
      String destImageName = args[2];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        throw new IllegalArgumentException("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image filteredImage = applyFilter(destImageName, sourceImage);
        imageStorageModel.insertImage(filteredImage);
        return "Completed " + filterName + " operation.";
      }
    }
  }

  @Override
  public String getUsage() {
    return filterName + "image-name dest-image-name: " + filterName + "the given image and\n "
            + "store the result in another image with the given name.";
  }

  //helper method to apply a filter to an image and return the result
  private Image applyFilter(String destImageName, Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    double[][] filter = filters.getFilter(filterName);
    int filterSize = filter.length;

    Image filteredImage = new SimpleImage(width, height, destImageName);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double redSum = 0.0;
        double greenSum = 0.0;
        double blueSum = 0.0;

        //compute the filter value for a given pixel
        for (int i = -filterSize / 2; i <= filterSize / 2; i++) {
          for (int j = -filterSize / 2; j <= filterSize / 2; j++) {
            if (x + j >= 0 && x + j < width && y + i >= 0 && y + i < height) {
              Pixel neighbor = image.getPixel(x + j, y + i);

              redSum += neighbor.getRed() * filter[i + filterSize / 2][j + filterSize / 2];
              greenSum += neighbor.getGreen() * filter[i + filterSize / 2][j + filterSize / 2];
              blueSum += neighbor.getBlue() * filter[i + filterSize / 2][j + filterSize / 2];
            }
          }
        }

        Pixel filteredPixel = new Pixel((int) redSum, (int) greenSum, (int) blueSum);
        filteredImage.setPixel(x, y, filteredPixel);
      }
    }
    return filteredImage;
  }

}