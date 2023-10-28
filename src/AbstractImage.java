public class AbstractImage implements Image {

  //width and height of image, measured in pixels
  private final int width;
  private final int height;

  //name of image
  private final String name;

  //pixels in image
  private Pixel[][] imageBody;

  /**
   * Abstract constructor for an Image.
   *
   * @param width  int, width of image
   * @param height int, height of image
   * @param name   String, name of image
   */
  public AbstractImage(int width, int height, String name) {
    this.width = width;
    this.height = height;
    this.name = name;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Image applyFilter(double[][] filter, String editedName) {
    return null;
  }

  @Override
  public Pixel getPixel(int x, int y) throws IndexOutOfBoundsException {
    return null;
  }
}
