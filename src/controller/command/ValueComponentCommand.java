package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command computes and stores the value component of an image.
 */
public class ValueComponentCommand implements CommandController {

  //model state
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of the image database
   */
  public ValueComponentCommand(ImageStorageModel imageStorageModel) {
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
        Image resultImage = getValueComponent(sourceImage, resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed value-component operation.";
    }
  }

  @Override
  public String getUsage() {
    return "value-component image-name dest-image-name: Create an image with the\n "
            + "value-component of the image with the given name, and refer to it henceforth in\n"
            + "the program by the given destination name.";
  }


  //helper method to return the value component of an image
  private Image getValueComponent(Image source, String resultImageName) {
    Image valueImage = new SimpleImage(source.getWidth(),
            source.getHeight(),
            resultImageName);

    for (int x = 0; x < source.getWidth(); x++) {
      for (int y = 0; y < source.getHeight(); y++) {
        Pixel currPixel = source.getPixel(x, y);

        valueImage.setPixel(x, y, new Pixel(currPixel.getValue()));
      }
    }
    return valueImage;
  }
}
