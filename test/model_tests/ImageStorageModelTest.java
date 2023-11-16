package model_tests;

import org.junit.Test;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * This class tests the methods and features of ImageStorageModel.
 */
public class ImageStorageModelTest {

  /**
   * Test ImageStoreModel's getSize(), insertImage(), and getImage() methods.
   */
  @Test
  public void testInsertAndGetImage() {
    ImageStorageModel model = new ImageStorageModel();

    //model should be empty
    assertEquals(0, model.getSize());
    assertNull(model.getImage("should be null"));

    //add an image
    model.insertImage(ModelTestUtilities.constructBasicImage());
    Image retVal = model.getImage("default");
    assertNotNull(retVal);
    assertEquals("default", retVal.getName());
    assertEquals(3, retVal.getHeight());
    assertEquals(3, retVal.getWidth());

    //overwrite an image
    model.insertImage(new SimpleImage(5, 5, "default"));
    retVal = model.getImage("default");
    assertNotNull(retVal);
    assertEquals("default", retVal.getName());
    assertEquals(5, retVal.getHeight());
    assertEquals(5, retVal.getWidth());
  }

  /**
   * Test ImageStoreModel's remove() method.
   */
  @Test
  public void testRemoveImage() {
    ImageStorageModel model = new ImageStorageModel();

    //try removing an image
    assertThrows(IllegalArgumentException.class,
        () -> model.removeImage("default"));

    //model should be empty
    assertEquals(0, model.getSize());
    assertNull(model.getImage("should be null"));

    //add an image
    model.insertImage(ModelTestUtilities.constructBasicImage());
    Image retVal = model.getImage("default");
    assertNotNull(retVal);
    assertEquals("default", retVal.getName());
    assertEquals(3, retVal.getHeight());
    assertEquals(3, retVal.getWidth());

    //try removing added image
    model.removeImage("default");
    assertEquals(0, model.getSize());
  }

  /**
   * Empty test for ImageStorageModel and Image.
   */
  @Test
  public void testImageEmptyStorageModel() {
    ImageStorageModel model = new ImageStorageModel();

    //model should be empty
    assertEquals(0, model.getSize());
    assertNull(model.getImage("should be null"));

    //modify added image
    Image img = model.getImage("default");
    img.setPixel(0, 0, new Pixel(-10, 50, 550));
    Image result = model.getImage("default");
    assertEquals(0, result.getPixel(0, 0).getRed());
    assertEquals(50, result.getPixel(0, 0).getGreen());
    assertEquals(255, result.getPixel(0, 0).getBlue());
  }


}
