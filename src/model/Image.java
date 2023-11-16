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
   * Method to return the deep copy of a pixel at a given index in the image.
   *
   * @param x int, x-pos of pixel
   * @param y int, y-pos of pixel
   * @return model.Pixel at given index
   * @throws IndexOutOfBoundsException if x,y is outside of image dimensions
   */
  Pixel getPixel(int x, int y) throws IndexOutOfBoundsException;


  /**
   * Method to return the red component of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image containing the red component of this image
   */
  Image getRedComponent(String resultImageName);

  /**
   * Method to return the green component of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image containing the green component of this image
   */
  Image getGreenComponent(String resultImageName);

  /**
   * Method to return the blue component of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image containing the blue component of this image
   */
  Image getBlueComponent(String resultImageName);

  /**
   * Method to return the value component of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image containing the value component of this image
   */
  Image getValueComponent(String resultImageName);

  /**
   * Method to return the intensity component of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image containing the intensity component of this image
   */
  Image getIntensityComponent(String resultImageName);

  /**
   * Method to return the luma component of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image containing the luma component of this image
   */
  Image getLumaComponent(String resultImageName);

  /**
   * Method to return the horizontal flip of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image, depicting this image flipped horizontally
   */
  Image getHorizontalFlip(String resultImageName);

  /**
   * Method to return the vertical flip of an image.
   *
   * @param resultImageName String, the name of the resulting image
   * @return a new Image, depicting this image flipped vertically
   */
  Image getVerticalFlip(String resultImageName);

  /**
   * Method to brighten an image by a certain increment.
   *
   * @param increment       int, increment by which to brighten image
   * @param resultImageName String, name of the resulting image
   * @return Image, this brightened by a given increment
   */
  Image brighten(int increment, String resultImageName);

  /**
   * Method to apply a filter to an image.
   *
   * @param kernel          double[][], the filter kernel
   * @param resultImageName String, name of the resulting image
   * @return a new Image, depicting this image filtered
   */
  Image applyFilter(double[][] kernel, String resultImageName);

  /**
   * Method to apply a linearColorTransformation to an image.
   *
   * @param kernel          double[][], the LCT kernel
   * @param resultImageName String, name of resulting image
   * @return a new Image, depicting this image transformed
   */
  Image applyLinearColorTransformation(double[][] kernel, String resultImageName);

  /**
   * Method to compress an image.
   *
   * @param compressedImageName name of the compressed image
   * @return the compressed version of this image
   */
  Image compressImage(int compressionRatio, String compressedImageName);

  /**
   * Method to generate the histogram of an image.
   *
   * @param histogramName name of the generated histogram
   * @return the histogram of a given image
   */
  Image getHistogram(String histogramName);

  /**
   * Method to color-correct an image.
   *
   * @param resultImageName name of the color-corrected image
   * @return the color-corrected version of this image
   */
  Image colorCorrectImage(String resultImageName);

  /**
   * Method to adjust the levels of an image.
   *
   * @param resultImageName name of the level-adjusted image
   * @param b               int, the black value for the level adjustment
   * @param m               int, the mid value for the level adjustment
   * @param w               int, the white value for the level adjustment
   * @return the level-adjusted version of this image
   */
  Image adjustLevels(String resultImageName, int b, int m, int w);


}
