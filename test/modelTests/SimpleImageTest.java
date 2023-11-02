package modelTests;

import org.junit.Test;

import model.SimpleImage;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * This class tests the methods of the SimpleImage class.
 */
public class SimpleImageTest {

  /**
   * Simple test for constructor validity.
   */
  @Test
  public void testValidConstructor() {
    try {
      Image img = new SimpleImage(5, 5, "default");
      assertEquals(5, img.getWidth());
      assertEquals(5, img.getHeight());
      assertEquals("default", img.getName());
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
            () -> new SimpleImage(0, 10, "img"));
    assertThrows(IllegalArgumentException.class,
            () -> new SimpleImage(10, 0, "img"));
  }

  /**
   * Simple tests for setPixel() and getPixel().
   */
  @Test
  public void testSetAndGetPixel() {
    Image img = ModelTestUtilities.constructBasicImage();

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

//  /**
//   * A simple test for applyFilter(), blur.
//   */
//  @Test
//  public void testApplyFilterBlur() {
//    Filter filters = new Filter();
//
//    /*
//      Apply blur -
//      RED
//      10 0  30       5.6250 7.5000 8.1250
//      20 10 0    ->  15.000 15.000 10.000
//      50 20 30       18.125 17.500 10.625
//
//      GREEN
//      40 30 20       19.375 26.250 19.375
//      20 50 60   ->  25.000 38.750 35.000
//      50 10 70       19.375 28.750 29.375
//
//      BLUE
//      30 30 80       13.750 26.875 30.000
//      10 20 40   ->  17.500 28.750 27.500
//      40 30 10       16.250 19.375 12.500
//     */
//
//    double[][] redResult = {
//            {5, 7, 8},
//            {15, 15, 10},
//            {18, 17, 10}
//    };
//
//    double[][] greenResult = {
//            {19, 26, 19},
//            {25, 38, 35},
//            {19, 28, 29}
//    };
//
//    double[][] blueResult = {
//            {13, 26, 30},
//            {17, 28, 27},
//            {16, 19, 12}
//    };
//
//    Image img = constructBasicImage();
//    Image blurImg = img.applyFilter(filters.getFilter("BlUr"), "blurImg");
//
//    //test img for correctness (should not have changed)
//    verifyImage(imgRed, img, 0);
//    verifyImage(imgGreen, img, 1);
//    verifyImage(imgBlue, img, 2);
//
//    //test blur for correctness
//    assertEquals("blurImg", blurImg.getName());
//    verifyImage(redResult, blurImg, 0);
//    verifyImage(greenResult, blurImg, 1);
//    verifyImage(blueResult, blurImg, 2);
//  }
//
//  /**
//   * A simple test for applyFilter(), sharpen.
//   */
//  @Test
//  public void testApplyFilterSharpen() {
//    Filter filters = new Filter();
//
//    /*
//      Apply sharpen -
//      RED
//      10 0  30       1.2500 5.0000 16.250
//      20 10 0    ->  35.000 50.000 12.500
//      50 20 30       53.750 42.500 23.750
//
//      GREEN
//      40 30 20       38.750 61.250 31.250
//      20 50 60   ->  46.250 125.00 91.250
//      50 10 70       42.500 61.250 80.000
//
//      BLUE
//      30 30 80       20.000 65.000 87.500
//      10 20 40   ->  31.250 87.500 72.500
//      40 30 10       31.250 42.500 8.7500
//     */
//
//    double[][] redResult = {
//            {1, 5, 16},
//            {35, 50, 12},
//            {53, 42, 23}
//    };
//
//    double[][] greenResult = {
//            {38, 61, 31},
//            {46, 125, 91},
//            {42, 61, 80}
//    };
//
//    double[][] blueResult = {
//            {20, 65, 87},
//            {31, 87, 72},
//            {31, 42, 8}
//    };
//
//    Image img = constructBasicImage();
//    Image sharpenedImg = img.applyFilter(filters.getFilter("SHARPEN"),
//            "sharpenedImg");
//
//    //test img for correctness (should not have changed)
//    verifyImage(imgRed, img, 0);
//    verifyImage(imgGreen, img, 1);
//    verifyImage(imgBlue, img, 2);
//
//    //test sharpen for correctness
//    assertEquals("sharpenedImg", sharpenedImg.getName());
//    verifyImage(redResult, sharpenedImg, 0);
//    verifyImage(greenResult, sharpenedImg, 1);
//    verifyImage(blueResult, sharpenedImg, 2);
//  }
//
//
//  /**
//   * A simple test for applyLinearColorTransformation().
//   */
//  @Test
//  public void testApplyLinearColorTransformation() {
//
//    //this is essentially just matrix multiplication, so we will only test one transformation
//    LinearColorTransformation transformation = new LinearColorTransformation();
//
//    /*
//      Apply greyscale -
//      RED
//      10 0  30
//      20 10 0
//      50 20 30
//
//      GREEN
//      40 30 20       32.900 23.620 26.458
//      20 50 60   ->  19.278 39.330 45.800 (for all three channels)
//      50 10 70       49.278 13.570 57.164
//
//      BLUE
//      30 30 80
//      10 20 40
//      40 30 10
//    */
//
//    double[][] result = {
//            {32, 23, 26},
//            {19, 39, 45},
//            {49, 13, 57}
//    };
//
//    Image img = constructBasicImage();
//    Image greyscaleImg = img.applyLinearColorTransformation(
//            transformation.getLinearTransformation("greYscAle"),
//            "greyscaleImg");
//
//    //test img for correctness (should not have changed)
//    verifyImage(imgRed, img, 0);
//    verifyImage(imgGreen, img, 1);
//    verifyImage(imgBlue, img, 2);
//
//    //test sharpen for correctness
//    assertEquals("greyscaleImg", greyscaleImg.getName());
//    verifyImage(result, greyscaleImg, 0);
//    verifyImage(result, greyscaleImg, 1);
//    verifyImage(result, greyscaleImg, 2);
//  }
//
//  /**
//   * A simple test for applyBrighten().
//   */
//  @Test
//  public void testApplyBrighten() {
//    /*
//      Apply brighten(200) -
//      RED
//      10 0  30       210 200 230
//      20 10 0    ->  220 210 200
//      50 20 30       250 220 230
//
//      GREEN
//      40 30 20       240 230 220
//      20 50 60   ->  220 250 255
//      50 10 70       250 210 255
//
//      BLUE
//      30 30 80       230 230 255
//      10 20 40   ->  210 220 240
//      40 30 10       240 230 210
//     */
//
//    double[][] redResult = {
//            {210, 200, 230},
//            {220, 210, 200},
//            {250, 220, 230}
//    };
//
//    double[][] greenResult = {
//            {240, 230, 220},
//            {220, 250, 255},
//            {250, 210, 255}
//    };
//
//    double[][] blueResult = {
//            {230, 230, 255},
//            {210, 220, 240},
//            {240, 230, 210}
//    };
//
//    Image img = constructBasicImage();
//    Image brightenedImg = img.applyBrighten(200, "brightenedImg");
//
//    //test img for correctness (should not have changed)
//    verifyImage(imgRed, img, 0);
//    verifyImage(imgGreen, img, 1);
//    verifyImage(imgBlue, img, 2);
//
//    //test sharpen for correctness
//    assertEquals("brightenedImg", brightenedImg.getName());
//    verifyImage(redResult, brightenedImg, 0);
//    verifyImage(greenResult, brightenedImg, 1);
//    verifyImage(blueResult, brightenedImg, 2);
//
//       /*
//      Apply brighten(-50) -
//      RED
//      10 0  30       0 0 0
//      20 10 0    ->  0 0 0
//      50 20 30       0 0 0
//
//      GREEN
//      40 30 20       0 0 0
//      20 50 60   ->  0 0 10
//      50 10 70       0 0 20
//
//      BLUE
//      30 30 80       0 0 30
//      10 20 40   ->  0 0 0
//      40 30 10       0 0 0
//     */
//
//    redResult = new double[][]{
//            {0, 0, 0},
//            {0, 0, 0},
//            {0, 0, 0}
//    };
//
//    greenResult = new double[][]{
//            {0, 0, 0},
//            {0, 0, 10},
//            {0, 0, 20}
//    };
//
//    blueResult = new double[][]{
//            {0, 0, 30},
//            {0, 0, 0},
//            {0, 0, 0}
//    };
//
//    img = constructBasicImage();
//    Image darkenedImg = img.applyBrighten(200, "brightenedImg");
//
//    //test img for correctness (should not have changed)
//    verifyImage(imgRed, img, 0);
//    verifyImage(imgGreen, img, 1);
//    verifyImage(imgBlue, img, 2);
//
//    //test sharpen for correctness
//    assertEquals("brightenedImg", brightenedImg.getName());
//    verifyImage(redResult, brightenedImg, 0);
//    verifyImage(greenResult, brightenedImg, 1);
//    verifyImage(blueResult, brightenedImg, 2);
//  }
//
//  /**
//   * A simple test for getLumaImage().
//   */
//  @Test
//  public void testGetLumaImage() {
//
//    double[][] redResult = {
//            {},
//            {},
//            {}
//    };
//
//    double[][] greenResult = {
//            {},
//            {},
//            {}
//    };
//
//    double[][] blueResult = {
//            {},
//            {},
//            {}
//    };
//  }
//
//  /**
//   * A simple test for getIntensityImage().
//   */
//  @Test
//  public void testGetIntensityImage() {
//
//  }
//
//  /**
//   * A simple test for getValueImage().
//   */
//  @Test
//  public void testGetValueImage() {
//
//  }
//
//  /**
//   * A simple test for getRedImage().
//   */
//  @Test
//  public void testGetRedImage() {
//
//  }
//
//  /**
//   * A simple test for getGreenImage().
//   */
//  @Test
//  public void testGetGreenImage() {
//
//  }
//
//  /**
//   * A simple test for getBlueImage().
//   */
//  @Test
//  public void testGetBlueImage() {
//
//  }
//
//  /**
//   * A simple test for getHorizontalImage().
//   */
//  @Test
//  public void testGetHorizontalFlip() {
//
//  }
//
//  /**
//   * A simple test for getVerticalFlip().
//   */
//  @Test
//  public void testGetVerticalFlip() {
//
//  }

}
