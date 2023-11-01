package controller.command;

import model.ImageModel;

public class HorizontalFlipCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public HorizontalFlipCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: horizontal-flip image-name dest-image-name");
    } else {
      String sourceImageName = args[1];
      String destImageName = args[2];
      Image sourceImage = imageStorageModel.getImage(sourceImageName);
      Image destImage = horizontalFlip(sourceImage, destImageName);
      imageStorageModel.insertImage(destImage);
    }
  }

  //helper method to return the horizontally flipped image
  private Image horizontalFlip(Image image, String destImageName) {
    int height = image.getHeight();
    int width = image.getWidth();

    Image newImage = new SimpleImage(width, height, destImageName);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = image.getPixel(i, j);

        //horizontal flip -> new x coordinate
        int flippedX = width - i - 1;

        newImage.setPixel(flippedX, j, currPixel);
      }
    }
    return newImage;
  }
}
