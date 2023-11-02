package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the red component of an image.
 */
public class RedComponentCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RedComponentCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      return "Invalid input. Usage: red-component image-name dest-image-name";
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        return "Invalid request. Image with name + " + sourceImageName
                + "not found.";
      } else {
        Image resultImage = getRedComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed red-component operation.";
      }
    }
  }

  @Override
  public String getUsage() {
    return "red-component image-name dest-image-name: Create an image with the\n " +
            "red-component of the image with the given name, and refer to it henceforth in\n" +
            "the program by the given destination name.";
  }

  //helper function to return the green component of an image
  private Image getRedComponent(Image source, String resultImageName) {
    SimpleImage redImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int i = 0; i < source.getHeight(); i++) {
      for (int j = 0; j < source.getWidth(); j++) {
        redImage.setPixel(j, i, new Pixel(source.getPixel(j, i).getRed(), 0, 0));
      }
    }
    return redImage;
  }
}
