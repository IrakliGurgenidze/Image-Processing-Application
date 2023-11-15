package controller_tests.command_tests;

import model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test class for BrightenCommand.
 */
public class BrightenCommandTest extends AbstractCommandTest{

  @Override
  public void testCommand(){
    testPositive();
    testNegative();
  }

  /**
   * Tests the positive increment.
   */
  public void testPositive(){
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

  public void testNegative(){
    String[] loadBase = loadImage("manhattan-small.png", "man");
    imageController.runCommand(loadBase);
    assertEquals(3, imageModel.getSize());

    String[] funcCmd = imageController.parseCommand("brighten -50 man mandarker");
    imageController.runCommand(funcCmd);
    assertEquals(imageModel.getSize(), 4);

    String[] loadExpected = loadImage("manhattan-small-darker-by-50.png", "manbr");
    imageController.runCommand(loadExpected);
    assertEquals(imageModel.getSize(), 4);

    Image expected = imageModel.getImage("manbr");
    Image result = imageModel.getImage("mandarker");
    assertTrue(isEqual(expected, result));
  }
}
