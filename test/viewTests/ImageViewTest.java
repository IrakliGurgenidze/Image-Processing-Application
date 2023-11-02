package viewTests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import view.ImageView;

public class ImageViewTest {
  private ImageView imageView;
  private OutputStream out;
  private InputStream in;

  @Before
  public void setup(){
    out = new ByteArrayOutputStream();
    String inputString = "Testing this line";
    in = new ByteArrayInputStream(inputString.getBytes());
    imageView = new ImageView(in, out);
  }

  @Test
  public void testDisplayStatus() throws IOException {
    imageView.displayStatus("TEST");
    assertEquals("TEST\n", out.toString());
  }

  @Test
  public void testGetInput(){
    assertEquals("Testing this line", imageView.getInput());
  }

}
