/**
 * Implementation of a color image.
 */
public class ColorImage extends AbstractImage {

  /**
   * Public constructor for a color image.
   *
   * @param width  int, width of image
   * @param height int, height of image
   * @param name   String, name of image
   */
  public ColorImage(int width, int height, String name) {
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
