package controller_tests.command_tests;

import org.junit.Test;

import model.Image;
import model_tests.ModelTestUtilities;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit Test class for LevelsAdjustCommand.
 */
public class LevelsAdjustCommandTest extends AbstractCommandTest {

  /**
   * Test the command with the provided sample images.
   */
  @Test
  public void testLevelsAdjustCommand() {
    Image testImage = ModelTestUtilities.constructBasicImage();
    imageModel.insertImage(testImage);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("levels-adjust 20 100 255 default adj");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    Image adj = imageModel.getImage("adj");

    int[][][] expected = {
            {{0, 36, 18}, {0, 18, 18}, {18, 0, 100}},
            {{0, 0, 0}, {0, 53, 0}, {0, 69, 36}},
            {{53, 53, 36}, {0, 0, 18}, {18, 85, 0}}
    };

    for (int x = 0; x < adj.getWidth(); x++) {
      for (int y = 0; y < adj.getHeight(); y++) {
        assertEquals(adj.getPixel(x, y).getRed(), expected[y][x][0]);
        assertEquals(adj.getPixel(x, y).getGreen(), expected[y][x][1]);
        assertEquals(adj.getPixel(x, y).getBlue(), expected[y][x][2]);
      }
    }
  }
}

