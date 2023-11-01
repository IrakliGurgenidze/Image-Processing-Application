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
   * @param red int, value of red channel
   * @param green int, value of green channel
   * @param blue int, value of blue channel
   */
  public Pixel(int red, int green, int blue) {
    this.red = clamp(red, 0, 255);
    this.green = clamp(green, 0,255);
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

  //helper function to clamp a pixel value within 0-255
  //FIXME may need to change clamp values based on image filetype
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
    return (double) (red + green + blue) / 3.0;
  }

  /**
   * Returns the luma of this pixel.
   *
   * @return double, the luma of this pixel
   */
  public double getLuma() {
    return 0.216 * red + 0.7152 * green + 0.0722 * blue;
  }


  // ----------- getters and setters ----------- //
  public int getRed() {
    return red;
  }

  public void setRed(int red) { this.red = clamp(red, 0, 255); }

  public int getGreen() {
    return green;
  }

  public void setGreen(int green) {
    this.green = clamp(green, 0, 255);
  }

  public int getBlue() {
    return blue;
  }

  public void setBlue(int blue) {
    this.blue = clamp(blue, 0, 255);
  }

  //sets all pixels to an equal value
  public void setAll(int value) {
    int clampedValue = clamp(value, 0, 255);
    this.red = clampedValue;
    this.green = clampedValue;
    this.blue = clampedValue;
  }
}
