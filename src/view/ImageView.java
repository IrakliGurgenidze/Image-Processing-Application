package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * An implementation of View for the Image Processor.
 */
public class ImageView implements View {
  private final OutputStream out;
  private InputStream in;
  private Scanner scan;

  public ImageView(InputStream in, OutputStream out){
    this.in = in;
    this.out = out;
    this.scan = new Scanner(this.in);
  }

  @Override
  public void displayStatus(String status) throws IOException {
    if(!status.equals(">> ")) {
      status = status + "\n";
    }
    this.out.write(status.getBytes());
  }

  @Override
  public String getInput(){
    return scan.nextLine();
  }
}
