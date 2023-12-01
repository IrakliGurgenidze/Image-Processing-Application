package modeltests;

import org.junit.Test;

import model.Image;
import model.SimpleImage;

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
  public void testGetPixel() {
    Image img = ModelTestUtilities.constructBasicImage();

    //calls should throw IndexOutOfBoundsException
    assertThrows(IndexOutOfBoundsException.class, () -> img.getPixel(-1, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> img.getPixel(0, -1));
    assertThrows(IndexOutOfBoundsException.class, () -> img.getPixel(3, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> img.getPixel(0, 3));

    //verify that pixel is returned correctly
    assertEquals(10, img.getPixel(0, 0).getRed());
    assertEquals(40, img.getPixel(0, 0).getGreen());
    assertEquals(30, img.getPixel(0, 0).getBlue());
  }


}
