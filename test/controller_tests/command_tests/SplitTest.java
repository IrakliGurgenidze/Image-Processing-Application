package controller_tests.command_tests;

import model.Image;
import model.Pixel;
import model_tests.ModelTestUtilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the SplitUtil class.
 */
public class SplitTest extends AbstractCommandTest {

  /**
   * Tests trying to split 100% (should throw exception).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testUpperLimit() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    loadBase = loadImage("manhattan-small-blur.png", "manblur");
    imageController.runCommand(loadBase);
    assertEquals(2, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("blur man res split 100");
    imageController.runCommand(funcCmd);
  }

  /**
   * Tests trying to split 0% (should throw exception).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLowerLimit() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    loadBase = loadImage("manhattan-small-blur.png", "manblur");
    imageController.runCommand(loadBase);
    assertEquals(2, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("blur man res split 0");
    imageController.runCommand(funcCmd);
  }

  /**
   * Tests split with the filter commands.
   */
  @Test
  public void testFilterSplitParam() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    loadBase = loadImage("manhattan-small-blur.png", "manblur");
    imageController.runCommand(loadBase);
    assertEquals(2, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("blur man res split 50");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);

    Image resImage = imageModel.getImage("res");
    Image man = imageModel.getImage("man");
    Image manBlur = imageModel.getImage("manblur");

    int height = resImage.getHeight();
    int width = resImage.getWidth();
    int pct = (int) (width * (50 / 100.0));

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = resImage.getPixel(x, y);
        if (x < pct) {
          assertEquals(currPixel.getRed(), manBlur.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), manBlur.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), manBlur.getPixel(x, y).getBlue());
        } else {
          assertEquals(currPixel.getRed(), man.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), man.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), man.getPixel(x, y).getBlue());
        }
      }
    }
  }

  /**
   * Tests split with the Linear Color Transformation commands.
   */
  @Test
  public void testLCTSplitParam() {
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    loadBase = loadImage("manhattan-small-sepia.png", "mansepia");
    imageController.runCommand(loadBase);
    assertEquals(2, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("sepia man res split 30");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);

    Image resImage = imageModel.getImage("res");
    Image man = imageModel.getImage("man");
    Image manSepia = imageModel.getImage("mansepia");

    int height = resImage.getHeight();
    int width = resImage.getWidth();
    int pct = (int) (width * (30 / 100.0));

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = resImage.getPixel(x, y);
        if (x < pct) {
          assertEquals(currPixel.getRed(), manSepia.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), manSepia.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), manSepia.getPixel(x, y).getBlue());
        } else {
          assertEquals(currPixel.getRed(), man.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), man.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), man.getPixel(x, y).getBlue());
        }
      }
    }
  }

  /**
   * Tests split with the Color Correct command.
   */
  @Test
  public void testColorCorrectSplitParam() {
    String[] loadBase = loadImage("galaxy-tinge.png", "gt");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    loadBase = loadImage("galaxy-corrected.png", "gc");
    imageController.runCommand(loadBase);
    assertEquals(2, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("color-correct gt res split 25");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 3);

    Image resImage = imageModel.getImage("res");
    Image gal = imageModel.getImage("gt");
    Image galc = imageModel.getImage("gc");

    int height = resImage.getHeight();
    int width = resImage.getWidth();
    int pct = (int) (width * (25 / 100.0));

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = resImage.getPixel(x, y);
        if (x < pct) {
          assertEquals(currPixel.getRed(), galc.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), galc.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), galc.getPixel(x, y).getBlue());
        } else {
          assertEquals(currPixel.getRed(), gal.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), gal.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), gal.getPixel(x, y).getBlue());
        }
      }
    }
  }

  /**
   * Tests split with the Levels Adjust command.
   */
  @Test
  public void testLevelsAdjustSplitParam() {
    Image testImage = ModelTestUtilities.constructBasicImage();
    imageModel.insertImage(testImage);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("levels-adjust 20 100 255 default res split 50");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    Image resImage = imageModel.getImage("res");

    int height = resImage.getHeight();
    int width = resImage.getWidth();
    int pct = (int) (width * (50 / 100.0));

    int[][][] expected = {
            {{0, 36, 18}, {0, 18, 18}, {18, 0, 100}},
            {{0, 0, 0}, {0, 53, 0}, {0, 69, 36}},
            {{53, 53, 36}, {0, 0, 18}, {18, 85, 0}}
    };

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = resImage.getPixel(x, y);
        if (x < pct) {
          assertEquals(currPixel.getRed(), expected[y][x][0]);
          assertEquals(currPixel.getGreen(), expected[y][x][1]);
          assertEquals(currPixel.getBlue(), expected[y][x][2]);
        } else {
          assertEquals(currPixel.getRed(), testImage.getPixel(x, y).getRed());
          assertEquals(currPixel.getGreen(), testImage.getPixel(x, y).getGreen());
          assertEquals(currPixel.getBlue(), testImage.getPixel(x, y).getBlue());
        }
      }
    }
  }
}
