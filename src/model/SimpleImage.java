package model;

import model.utilities.CompressionUtil;
import model.utilities.HistogramUtil;
import model.utilities.LevelsAdjustUtil;

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

  /**
   * The constructor to create an image from an array of pixels.
   *
   * @param name      String, name of image
   * @param imageBody String, pixel array to become the body of the image
   * @throws IllegalArgumentException if the image body is of illegal dimensions
   */
  public SimpleImage(String name, Pixel[][] imageBody) throws IllegalArgumentException {
    int width = imageBody[0].length;
    int height = imageBody.length;

    if (width < 1) {
      throw new IllegalArgumentException("Width and Height must be positive integers.");
    }

    this.width = width;
    this.height = height;
    this.name = name;
    this.imageBody = imageBody;
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

    //return a deep copy of the selected pixel
    return new Pixel(imageBody[y][x]);
  }

  @Override
  public Image getRedComponent(String resultImageName) {
    Pixel[][] redImage = new Pixel[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = this.getPixel(x, y);
        redImage[y][x] = new Pixel(currPixel.getRed(), 0, 0);
      }
    }

    return new SimpleImage(resultImageName, redImage);
  }

  @Override
  public Image getGreenComponent(String resultImageName) {
    Pixel[][] greenImage = new Pixel[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = this.getPixel(x, y);
        greenImage[y][x] = new Pixel(0, currPixel.getGreen(), 0);
      }
    }

    return new SimpleImage(resultImageName, greenImage);
  }


}
