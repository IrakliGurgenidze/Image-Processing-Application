package view_tests;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import view.script.ScriptViewImpl;

import static org.junit.Assert.assertEquals;

public class ScriptViewTest {
  private ScriptViewImpl scriptView;
  private OutputStream out;
  private InputStream in;

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    String inputString = "Testing this line";
    in = new ByteArrayInputStream(inputString.getBytes());
    scriptView = new ScriptViewImpl(in, out);
  }

  @Test
  public void testDisplayStatus() throws IOException {
    scriptView.displayStatus("TEST");
    assertEquals("TEST\n", out.toString());
  }

  @Test
  public void testGetInput() {
    assertEquals("Testing this line", scriptView.getInput());
  }

}
