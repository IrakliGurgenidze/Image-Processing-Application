import controller.Controller;
import controller.ImageController;
import model.ImageStorageModel;
import model.StorageModel;
import view.ImageView;
import view.View;

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

    //initialize model, view, and controller
    StorageModel imageStorageModel = new ImageStorageModel();
    View imageView = new ImageView(System.in, System.out);
    Controller imageController = new ImageController(imageStorageModel, imageView);

    //If command line arg -file exists
    if (args.length > 1 && args[0].equals("-file")) {
      imageController.go(args[1]);
    }

    //run controller
    imageController.go();
  }
}
