package controller_tests.command_tests;

import model.Pixel;
import model_tests.ModelTestUtilities;
import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for LevelsAdjustCommand.
 */
public class LevelsAdjustCommandTest extends AbstractCommandTest {

  /**
   * Test the command with the provided sample images.
   */
  @Test
  public void testLevelsAdjustCommand(){
    Image testImage = ModelTestUtilities.constructBasicImage();
    imageModel.insertImage(testImage);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("levels-adjust 20 100 255 default adj");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    Image adj = imageModel.getImage("adj");

    for(int x = 0; x < adj.getWidth(); x++){
      for(int y = 0; y < adj.getHeight(); y++){
        System.out.println(x + " " + y);
        assertEquals(adj.getPixel(x,y).getRed(), 0);
        assertEquals(adj.getPixel(x,y).getBlue(), 0);
        assertEquals(adj.getPixel(x,y).getGreen(), 0);
      }
    }
  }
}

