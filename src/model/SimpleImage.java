package model;

/**
 * Simple image implementation of Image interface.
 */
public class SimpleImage implements Image {

  //width and height of image, measured in pixels
  private final int width;
  private final int height;

  //name of image
  private final String name;

  //pixels in image
  private final Pixel[][] imageBody;

  /**
   * Constructor for simple image class.
   *
   * @param width  int, width of image
   * @param height int, height of image
   * @param name   String, name of image
   * @throws IllegalArgumentException if non-positive width and height entered
   */
  public SimpleImage(int width, int height, String name) throws IllegalArgumentException {
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("Width and Height must be positive integers.");
    }

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


}
