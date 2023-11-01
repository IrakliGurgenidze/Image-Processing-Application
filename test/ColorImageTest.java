import org.junit.Test;

import model.ColorImage;
import model.Filter;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class ColorImageTest {

  //rgb channel values for img
  private final double[][] imgRed = {
          {10, 0, 30},
          {20, 10, 0},
          {50, 20, 30}
  };

  private final double[][] imgGreen = {
          {40, 30, 20},
          {20, 50, 60},
          {50, 10, 70}
  };

  private final double[][] imgBlue = {
          {30, 30, 80},
          {10, 20, 40},
          {40, 30, 10}
  };

  private Image constructBasicImage() {

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

    Image img = new ColorImage(3, 3, "default");
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
  private void verifyImage(double[][] result, Image img, int channel)
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

  /**
   * Simple test for constructor validity.
   */
  @Test
  public void testValidConstructor() {
    try {
      Image img = new ColorImage(5, 5, "default");
    } catch (Exception e) {
      fail("Error thrown: " + e.getMessage());
    }
  }

  /**
   * Simple test for constructor invalidity.
   */
  @Test
  public void testInvalidConstructor() {

    //non-positive width and height
    assertThrows(IllegalArgumentException.class,
            () -> new ColorImage(0, 10, "img"));
    assertThrows(IllegalArgumentException.class,
            () -> new ColorImage(10, 0, "img"));
  }

  /**
   * Simple tests for set and get pixel.
   */
  @Test
  public void testSetAndGetPixel() {
    Image img = constructBasicImage();

    //calls should throw IndexOutOfBoundsException
    assertThrows(IndexOutOfBoundsException.class,
            () -> img.setPixel(-1, 0, new Pixel(0, 0, 0)));
    assertThrows(IndexOutOfBoundsException.class,
            () -> img.setPixel(0, -1, new Pixel(0, 0, 0)));
    assertThrows(IndexOutOfBoundsException.class,
            () -> img.setPixel(3, 0, new Pixel(0, 0, 0)));
    assertThrows(IndexOutOfBoundsException.class,
            () -> img.setPixel(0, 3, new Pixel(0, 0, 0)));

    //verify that pixel is returned correctly
    assertEquals(10, img.getPixel(0, 0).getRed());
    assertEquals(40, img.getPixel(0, 0).getGreen());
    assertEquals(30, img.getPixel(0, 0).getBlue());

    //set pixel value
    img.setPixel(0, 0, new Pixel(300, 50, -10));
    assertEquals(255, img.getPixel(0, 0).getRed());
    assertEquals(50, img.getPixel(0, 0).getGreen());
    assertEquals(0, img.getPixel(0, 0).getBlue());
  }

  /**
   * A simple test for applyFilter, blur.
   */
  @Test
  public void testApplyFilterBlur() {
    Filter filters = new Filter();

    /*
      Apply blur -
      RED
      10 0  30       5.6250 7.5000 8.1250
      20 10 0    ->  15.000 15.000 10.000
      50 20 30       18.125 17.500 10.625

      GREEN
      40 30 20       19.375 26.250 19.375
      20 50 60   ->  25.000 38.750 35.000
      50 10 70       19.375 28.750 29.375

      BLUE
      30 30 80       13.750 26.875 30.000
      10 20 40   ->  17.500 28.750 27.500
      40 30 10       16.250 19.375 12.500
     */

    double[][] redResult = {
            {5, 7, 8},
            {15, 15, 10},
            {18, 17, 10}
    };

    double[][] greenResult = {
            {19, 26, 19},
            {25, 38, 35},
            {19, 28, 29}
    };

    double[][] blueResult = {
            {13, 26, 30},
            {17, 28, 27},
            {16, 19, 12}
    };

    Image img = constructBasicImage();
    Image blur = img.applyFilter(filters.getFilter("BlUr"), "blurImg");

    //test img for correctness (should not have changed)
    verifyImage(imgRed, img, 0);
    verifyImage(imgGreen, img, 1);
    verifyImage(imgBlue, img, 2);

    //test blur for correctness
    assertEquals("blurImg", blur.getName());
    verifyImage(redResult, blur, 0);
    verifyImage(greenResult, blur, 1);
    verifyImage(blueResult, blur, 2);
  }

  /**
   * A simple test for applyFilter, sharpen.
   */
  @Test
  public void testApplyFilterSharpen() {
    Filter filters = new Filter();

    /*
      Apply sharpen -
      RED
      1 0 3       f
      2 1 0   ->  f
      5 2 3       f

      GREEN
      4 3 2       f
      2 5 6   ->  f
      5 1 7       f

      BLUE
      3 3 8       f
      1 2 4   ->  f
      4 3 1       f
    */

//    model.Image sharpen = img.applyFilter(filters.getFilter("SHARPEN"), "sharpenImg");
  }


  /**
   * A simple test for applyLinearColorTransformation.
   */
  public void testApplyLinearColorTransformation() {

  }



}
