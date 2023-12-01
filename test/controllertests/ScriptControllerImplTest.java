package controllertests;

import org.junit.Test;

import java.io.File;

import controllertests.commandtests.AbstractCommandTest;

import static org.junit.Assert.assertEquals;

/**
 * This class tests various controller methods.
 */
public class ScriptControllerImplTest extends AbstractCommandTest {

  /**
   * Test parseCommand.
   */
  @Test
  public void testParseCommand() {

    //parse basic command and check results
    String[] parsedCommand = imageController.parseCommand("load filepath.ppm man");
    assertEquals(3, parsedCommand.length);
    assertEquals("load", parsedCommand[0]);
    assertEquals("filepath.ppm", parsedCommand[1]);
    assertEquals("man", parsedCommand[2]);

    //a more complex example
    parsedCommand = imageController.parseCommand("load \"filepa th.ppm\" man");
    assertEquals(3, parsedCommand.length);
    assertEquals("load", parsedCommand[0]);
    assertEquals("filepa th.ppm", parsedCommand[1]);
    assertEquals("man", parsedCommand[2]);

    //spaces in both path and name
    parsedCommand = imageController.parseCommand("load \"filepa th.ppm\" \"m an\"");
    assertEquals(3, parsedCommand.length);
    assertEquals("load", parsedCommand[0]);
    assertEquals("filepa th.ppm", parsedCommand[1]);
    assertEquals("m an", parsedCommand[2]);

    //should also handle - in command entry
    parsedCommand = imageController.parseCommand("green-component \"filepa th.ppm\" \"m an\"");
    assertEquals(3, parsedCommand.length);
    assertEquals("green-component", parsedCommand[0]);
    assertEquals("filepa th.ppm", parsedCommand[1]);
    assertEquals("m an", parsedCommand[2]);
  }

  /**
   * A simple test of the run method in ScriptControllerImpl.
   */
  @Test
  public void testRun() {
    File script = new File(workingDirectory
            + File.separator
            + "controllertests"
            + File.separator
            + "testing_script.txt");
    imageController.run(script);
    assertEquals(10, imageModel.getSize());
  }


}
