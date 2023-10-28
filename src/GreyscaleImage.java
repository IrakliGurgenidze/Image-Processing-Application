/**
 * Implements a simple greyscale image.
 */
public class GreyscaleImage extends AbstractImage {

  /**
   * Public constructor for a greyscale image.
   *
   * @param width  int, width of image
   * @param height int, height of image
   * @param name   String, name of image
   */
  public GreyscaleImage(int width, int height, String name) {
    super(width, height, name);
  }

  @Override
  public Image applyFilter(double[][] filter, String editedName) {
    return null;
  }

  @Override
  public Image applyLinearColorTransformation(double[][] transformation, String editedName) {
    return null;
  }
}
