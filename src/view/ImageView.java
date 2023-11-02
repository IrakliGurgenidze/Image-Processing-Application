package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * An implementation of View for the Image Processor.
 */
public class ImageView implements View {
  private final OutputStream out;
  private final InputStream in;
  private final Scanner scan;

  public ImageView(InputStream in, OutputStream out) {
    this.out = out;
    this.scan = new Scanner(in);
  }

  @Override
  public void displayStatus(String status) throws IOException {
    if (!status.equals(">> ")) {
      status = status + "\n";
    }
    this.out.write(status.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String getInput() {
    return scan.nextLine();
  }
}
