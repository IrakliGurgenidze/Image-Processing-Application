package controller.command;

import model.Image;
import model.StorageModel;


/**
 * This command computes and stores the red component of an image.
 */
public class RedComponentCommand implements CommandController {

  //state of image database
  private final StorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public RedComponentCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    } else {
      String sourceImageName = args[1];
      String resultImageName = args[2];

      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                + " not found.");
      }
        Image resultImage = getRedComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed red-component operation.";
    }
  }

  @Override
  public String getUsage() {
    return "red-component image-name dest-image-name: Create an image with the\n "
            + "red-component of the image with the given name, and refer to it henceforth in\n"
            + "the program by the given destination name.";
  }

  //helper function to return the green component of an image
  private Image getRedComponent(Image source, String resultImageName) {
    SimpleImage redImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int x = 0; x < source.getWidth(); x++) {
      for (int y = 0; y < source.getHeight(); y++) {
        redImage.setPixel(x, y, new Pixel(source.getPixel(x, y).getRed(), 0, 0));
      }
    }
    return redImage;
  }
}
