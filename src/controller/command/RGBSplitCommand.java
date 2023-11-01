package controller.command;

import model.ImageStorageModel;

/**
 * This command stores the each RGB channel in an image seperately.
 */
public class RGBSplitCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RGBSplitCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 5) {
      System.out.println("Invalid input. Usage: " + getUsage());
    } else {
      String sourceImageName = args[1];
      String destImageRedName = args[2];
      String destImageGreenName = args[3];
      String destImageBlueName = args[4];

      redSplit(sourceImageName, destImageRedName);
      greenSplit(sourceImageName, destImageGreenName);
      blueSplit(sourceImageName, destImageBlueName);
    }
  }

  @Override
  public String getUsage() {
    return "rgb-split image-name dest-image-name-red dest-image-name-green\n " +
            "dest-image-name-blue: split the given image into three images containing\n" +
            " its red, green and blue components respectively. These would be the same\n " +
            "images that would be individually produced with the red-component,\n " +
            "green-component and blue-component commands.";
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
