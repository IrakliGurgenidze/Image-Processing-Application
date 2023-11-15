package controller_tests.command_tests;

import org.junit.Test;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for BrightenCommand.
 */
public class BrightenCommandTest extends AbstractCommandTest{

  /**
   * Tests the positive increment.
   */
  @Test
  public void testPositiveBrighten(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten 50 man manbrighter");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-brighter-by-50.png", "manbr");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("manbr");
    Image result = imageModel.getImage("manbrighter");
    assertTrue(isEqual(expected, result));
  }

  /**
   * Tests the negative increment.
   */
  @Test
  public void testNegativeBrighten(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(1, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten -50 man mandarker");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 2);

    String[] loadExpected = loadImage("manhattan-small-darker-by-50.png", "manbr");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 3);

    Image expected = imageModel.getImage("manbr");
    Image result = imageModel.getImage("mandarker");
    assertTrue(isEqual(expected, result));
  }
}
