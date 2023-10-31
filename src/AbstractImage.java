public abstract class AbstractImage implements Image {

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
    this.imageBody = new Pixel[height][width];
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

  private Pixel[][] getImageBody() {
    return imageBody;
  }

  private void setImageBody(Pixel[][] imageBody) {
    this.imageBody = imageBody;
  }

  @Override
  public Pixel getPixel(int x, int y) throws IndexOutOfBoundsException {
    if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
      throw new IndexOutOfBoundsException("Coordinates are out of bounds.");
    }
    return imageBody[y][x];
  }

  @Override
  public void setPixel(int x, int y, Pixel pixel) throws IndexOutOfBoundsException {
    if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
      throw new IndexOutOfBoundsException("Coordinates are out of bounds.");
    }
    imageBody[y][x] = pixel;
  }

  @Override
  public abstract Image applyFilter(double[][] filter, String editedName);

  @Override
  public abstract Image applyLinearColorTransformation(double[][] transformation,
                                                       String editedName);

  @Override
  public abstract Image applyBrighten(int increment, String editedName);


}
