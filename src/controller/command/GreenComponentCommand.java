package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the green component of an image.
 */
public class GreenComponentCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public GreenComponentCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: "  + getUsage());
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        System.out.println("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image resultImage = getGreenComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
      }
    }
  }

  @Override
  public String getUsage() {
    return "green-component image-name dest-image-name: Create an image with the\n " +
            "green-component of the image with the given name, and refer to it henceforth in\n" +
            "the program by the given destination name.";
  }

  //helper function to return the green component of an image
  private Image getGreenComponent(Image source, String resultImageName) {
    Image greenImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        greenImage.setPixel(i, j, new Pixel(0, source.getPixel(i, j).getGreen(), 0));
      }
    }
    return greenImage;
  }
}
