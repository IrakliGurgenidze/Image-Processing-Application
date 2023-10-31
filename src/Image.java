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
   * Method to apply a filter to an image. Filtered image is returned with name=editedName.
   *
   * @param filter     double[][], filter to be applied
   * @param editedName String, name of filtered image
   */
  Image applyFilter(double[][] filter, String editedName);



  /**
   * Method to apply a linear color transformation to an image. Transformed image is returned
   * with name = editedName
   *
   * @param transformation Double[][], transformation to be applied
   * @param editedName String, name of transformed image
   * @return the image with the applied linear transformation
   */
  Image applyLinearColorTransformation(double[][] transformation, String editedName);

  /**
   * Method to brighten (or darken) an image by a given increment.
   * @param increment int, the increment by which to brighten the image
   * @param editedName String, the resulting image name
   * @return the resulting Image
   */
  Image applyBrighten(int increment, String editedName);

  /**
   * Method to return a pixel at a given index in the image.
   *
   * @param x int, x-pos of pixel
   * @param y int, y-pos of pixel
   * @return Pixel at given index
   * @throws IndexOutOfBoundsException if x,y is outside of image dimensions
   */
  Pixel getPixel(int x, int y) throws IndexOutOfBoundsException;

  /**
   * Method to set a pixel at a given index in an image.
   * @param x int, x-pos of pixel
   * @param y int, y-pos of pixel
   * @param pixel Pixel object with specific rgb values
   * @throws IndexOutOfBoundsException if x,y is outside of image dimensions
   */
  void setPixel(int x, int y, Pixel pixel) throws IndexOutOfBoundsException;


}
