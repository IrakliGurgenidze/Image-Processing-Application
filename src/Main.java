import controller.ImageController;
import model.ImageStorageModel;
import view.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

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

    ImageStorageModel imageStorageModel = new ImageStorageModel();
    ImageController imageController = new ImageController(imageStorageModel);
    ImageView imageView = new ImageView(System.in, System.out);
    imageView.displayStatus("Image Processing Application by Rocky and Griffin");
    String executionStatus;

    //If command line arg -file exists
    if(args.length > 1 && args[0].equals("-file")){
      String scriptFileName = args[1];
      try{
        //ensure file exists
        File scriptFile = new File(scriptFileName);
      }catch(RuntimeException e){
        executionStatus = "Command line argument file not found.";
        imageView.displayStatus(executionStatus);
        //exit program if file not found
        System.exit(1);
      }
      //run arguments in script file
      String[] runArgs = {"run",scriptFileName};
      try{
        executionStatus = imageController.runCommand(runArgs);
        imageView.displayStatus(executionStatus);
      }catch (Exception e){
        //display exceptions from script file (if any) then quit program
        executionStatus = e.getMessage();
        imageView.displayStatus(executionStatus);
        System.exit(1);
      }
      //exit program after script file is run
      System.exit(0);
    }

    //run until break detected
    while (true) {
      imageView.displayStatus(">> ");
      String commandLine = imageView.getInput();

      String[] parsedCommand = imageController.parseCommand(commandLine);

      //run command but catch exceptions
      try {
        executionStatus = imageController.runCommand(parsedCommand);
      } catch(Exception e){
        executionStatus = e.getMessage();
      }

      //quit program
      if (executionStatus.equals("quit")) {
        imageView.displayStatus("Quitting program...");
        break;
      }

      imageView.displayStatus(executionStatus);
    }
  }


}
