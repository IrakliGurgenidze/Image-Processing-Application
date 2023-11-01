package model;

/**
 * Interface defining methods of a generic image representation.
 */
public interface Image {

  /**
   * Returns the width of the image by number of pixels.
   *
   * @return int, width of image
   */
  int getWidth();

  /**
   * Returns the height of the image by number of pixels.
   *
   * @return int, height of image
   */
  int getHeight();

  /**
   * Returns the name of the image.
   *
   * @return String, name of image
   */
  String getName();

  /**
   * Method to return a pixel at a given index in the image.
   *
   * @param x int, x-pos of pixel
   * @param y int, y-pos of pixel
   * @return model.Pixel at given index
   * @throws IndexOutOfBoundsException if x,y is outside of image dimensions
   */
  Pixel getPixel(int x, int y) throws IndexOutOfBoundsException;

  /**
   * Method to set a pixel at a given index in an image.
   *
   * @param x     int, x-pos of pixel
   * @param y     int, y-pos of pixel
   * @param pixel model.Pixel object with specific rgb values
   * @throws IndexOutOfBoundsException if x,y is outside of image dimensions
   */
  void setPixel(int x, int y, Pixel pixel) throws IndexOutOfBoundsException;


}
