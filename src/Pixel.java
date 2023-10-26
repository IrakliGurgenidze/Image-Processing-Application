/**
 * This class represents a singular pixel in an image.
 */
public class Pixel {

  //rgb channels
  private int red;
  private int green;
  private int blue;

  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
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
  public  double getLuma() {
    return 0.216 * red + 0.7152 * green + 0.0722 * blue;
  }

  // ----------- getters and setters ----------- //
  public int getRed() {
    return red;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public int getGreen() {
    return green;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public int getBlue() {
    return blue;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }


}
