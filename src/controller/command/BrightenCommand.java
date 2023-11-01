package controller.command;

import model.ImageModel;

public class BrightenCommand implements CommandController {
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public BrightenCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    try {
      int increment = Integer.parseInt(args[1]);
      String sourceImageName = args[2];
      String destImageName = args[3];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      if (sourceImage == null) {
        System.out.println("Invalid request. Image with name + " + sourceImageName
                + "not found.");
      } else {
        Image destImage = brighten(increment, sourceImage, destImageName);
        imageStorageModel.insertImage(destImage);
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid increment.");
    }
  }

  //helper function to compute and return a brightened image
  private Image brighten(int increment, Image image, String destImageName) {
    int height = image.getHeight();
    int width = image.getWidth();

    Image brightenedImage = new SimpleImage(width, height, destImageName);

    int red;
    int green;
    int blue;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = image.getPixel(i, j);

        red = currPixel.getRed() + increment;
        green = currPixel.getGreen() + increment;
        blue = currPixel.getBlue() + increment;

        brightenedImage.setPixel(i, j, new Pixel(red, green, blue));
      }
    }

    return brightenedImage;
  }


}
