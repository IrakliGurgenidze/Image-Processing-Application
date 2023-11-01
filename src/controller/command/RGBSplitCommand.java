package controller.command;

import model.ImageStorageStorageModel;

/**
 * This command stores the each RGB channel in an image seperately.
 */
public class RGBSplitCommand implements CommandController {

  //state of image database
  private final ImageStorageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RGBSplitCommand(ImageStorageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 5) {
      System.out.println("Invalid input. Usage: rgb-split image-name dest-image-name-red " +
              "dest-image-name-green dest-image-name-blue");
    } else {
      String sourceImageName = args[1];
      String destImageRedName = args[2];
      String destImageGreenName = args[3];
      String destImageBlueName = args[4];

        redSplit(sourceImageName, destImageRedName);
        greenSplit(sourceImageName, destImageGreenName);
        blueSplit(sourceImageName, destImageBlueName);

    }

  //store red channel
  private void redSplit(String imageName, String redName) {
    String[] args = {null, imageName, redName};
    RedComponentCommand redComponentCommand = new RedComponentCommand(imageStorageModel);
    redComponentCommand.execute(args);
  }

  //store green channel
  private void greenSplit(String imageName, String greenName) {
    String[] args = {null, imageName, greenName};
    GreenComponentCommand greenComponentCommand = new GreenComponentCommand(imageStorageModel);
    greenComponentCommand.execute(args);
  }

  //store blue channel
  private void blueSplit(String imageName, String blueName) {
    String[] args = {null, imageName, blueName};
    BlueComponentCommand blueComponentCommand = new BlueComponentCommand(imageStorageModel);
    blueComponentCommand.execute(args);
  }
}
