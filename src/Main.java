import controller.ImageController;
import model.ImageStorageModel;
import view.ImageView;
import java.util.Scanner;

/**
 * Main driver class for this project.
 */
public class Main {

  /**
   * Main method. Run this.
   *
   * @param args command line arguments, of which there are none.
   * @throws Exception if exception encountered.
   */
  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);

    ImageStorageModel imageStorageModel = new ImageStorageModel();
    ImageController imageController = new ImageController(imageStorageModel);
    ImageView imageView = new ImageView(System.in, System.out);

    imageView.displayStatus("Image Processing Application by Rocky and Griffin");
    //run until break detected
    while(true) {
      imageView.displayStatus(">> ");
      String commandLine = imageView.getInput();
      String[] parsedCommand = imageController.parseCommand(commandLine);
      String executionStatus = imageController.runCommand(parsedCommand);
      if(executionStatus.equals("quit")) {
        imageView.displayStatus("Quitting program...");
        break;
      }
      imageView.displayStatus(executionStatus);
    }
  }


}
