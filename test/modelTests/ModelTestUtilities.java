package modelTests;

import model.Image;
import model.Pixel;
import model.SimpleImage;

import static org.junit.Assert.assertEquals;

/**
 * This class implements various helper functions useful to all model tests.
 */
public class ModelTestUtilities {

  //rgb channel values for img
  public static final double[][] imgRed = {
          {10, 0, 30},
          {20, 10, 0},
          {50, 20, 30}
  };

  public static final double[][] imgGreen = {
          {40, 30, 20},
          {20, 50, 60},
          {50, 10, 70}
  };

  public static final double[][] imgBlue = {
          {30, 30, 80},
          {10, 20, 40},
          {40, 30, 10}
  };

  /**
   * Helper function to create a "standard" basic image.
   *
   * @return a basic image with the properties describes in method
   */
  public static Image constructBasicImage() {

    /*
      RED
      10 0  30
      20 10 0
      50 20 30

      GREEN
      40 30 20
      20 50 60
      50 10 70

      BLUE
      30 30 80
      10 20 40
      40 30 10
     */

    Image img = new SimpleImage(3, 3, "default");
    img.setPixel(0, 0, new Pixel(10, 40, 30));
    img.setPixel(0, 1, new Pixel(0, 30, 30));
    img.setPixel(0, 2, new Pixel(30, 20, 80));
    img.setPixel(1, 0, new Pixel(20, 20, 10));
    img.setPixel(1, 1, new Pixel(10, 50, 20));
    img.setPixel(1, 2, new Pixel(0, 60, 40));
    img.setPixel(2, 0, new Pixel(50, 50, 40));
    img.setPixel(2, 1, new Pixel(20, 10, 30));
    img.setPixel(2, 2, new Pixel(30, 70, 10));

    return img;
  }

  /**
   * Helper method to test the results of an image.
   *
   * @param result double array of expected values for a specific channel
   * @param img image to be checked
   * @param channel 0 for red, 1 for green, 2 for blue
   */
  public static void verifyImage(double[][] result, Image img, int channel)
  {
    for(int i = 0; i < img.getHeight(); i++) {
      for(int j = 0; j < img.getWidth(); j++) {
        if (channel == 0) {
          assertEquals(result[i][j], img.getPixel(i, j).getRed(), 0.0001);
        } else if (channel == 1) {
          assertEquals(result[i][j], img.getPixel(i, j).getGreen(), 0.0001);
        } else if (channel == 2) {
          assertEquals(result[i][j], img.getPixel(i, j).getBlue(), 0.0001);
        }
      }
    }
  }
}
