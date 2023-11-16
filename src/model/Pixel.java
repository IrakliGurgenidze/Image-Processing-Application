package model;

/**
 * This class represents a singular pixel in an image.
 */
public class Pixel {

  //rgb channels
  private int red;
  private int green;
  private int blue;

  /**
   * Public constructor for an RGB pixel, takes in values for all 3 channels.
   *
   * @param red   int, value of red channel
   * @param green int, value of green channel
   * @param blue  int, value of blue channel
   */
  public Pixel(int red, int green, int blue) {
    this.red = clamp(red, 0, 255);
    this.green = clamp(green, 0, 255);
    this.blue = clamp(blue, 0, 255);
  }

  /**
   * Public constructor for a greyscale pixel, takes in a single scalar that represents
   * the value of the pixel.
   *
   * @param value int, the value of the pixel
   */
  public Pixel(int value) {
    int clampedValue = clamp(value, 0, 255);
    this.red = clampedValue;
    this.green = clampedValue;
    this.blue = clampedValue;
  }

  /**
   * Public constructor for a copy of an existing pixel.
   *
   * @param pixel Pixel, pixel to be copied
   */
  public Pixel(Pixel pixel) {
    this.red = pixel.getRed();
    this.green = pixel.getGreen();
    this.blue = pixel.getBlue();
  }

  //helper function to clamp a pixel value within 0-255
  private int clamp(int value, int min, int max) {
    if (value < min) {
      return min;
    } else if (value > max) {
      return max;
    } else {
      return value;
    }
  }

  /**
   * Computes the value of this pixel.
   *
   * @return int, the value of this pixel
   */
  public int getValue() {
    int value = Math.max(this.red, this.green);
    return Math.max(value, this.blue);
  }

  /**
   * Returns the intensity of this pixel.
   *
   * @return double, the intensity of this pixel
   */
  public double getIntensity() {
    return ((double) red + (double) green + (double) blue) / 3;
  }

  /**
   * Returns the luma of this pixel.
   *
   * @return double, the luma of this pixel
   */
  public double getLuma() {
    return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
  }


  // ----------- getters and setters ----------- //

  /**
   * Getter for pixel's red channel value.
   *
   * @return int, value of pixel's red channel
   */
  public int getRed() {
    return red;
  }

  /**
   * Setter for pixel's red channel.
   *
   * @param red int, value to set red channel to
   */
  public void setRed(int red) {
    this.red = clamp(red, 0, 255);
  }

  /**
   * Getter for pixel's green channel value.
   *
   * @return int, value of pixel's green channel
   */
  public int getGreen() {
    return green;
  }

  /**
   * Setter for pixel's green channel.
   *
   * @param green int, value to set green channel to
   */
  public void setGreen(int green) {
    this.green = clamp(green, 0, 255);
  }

  /**
   * Getter for pixel's blue channel value.
   *
   * @return int, value of pixel's blue channel
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Setter for pixel's blue channel.
   *
   * @param blue int, value to set blue channel to
   */
  public void setBlue(int blue) {
    this.blue = clamp(blue, 0, 255);
  }


}
