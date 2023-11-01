package controller.command;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;

/**
 * This command saves an image from the database to a local location.
 */
public class SaveImageCommand implements CommandController {

  //state of image database
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the command.
   *
   * @param imageStorageModel state of image database
   */
  public SaveImageCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public void execute(String[] args) {
    if (args.length != 3) {
      System.out.println("Invalid input. Usage: save image-path image-name");
    } else {
      String imagePath = args[1];
      String imageName = args[2];

      Image image = imageStorageModel.getImage(imageName);
      if (image == null) {
        System.out.println("Image with name " + imageName + " not found.");
      } else {
        try {
          saveImage(image, imagePath);
        } catch (IOException e) {
          System.out.println("Invalid path.");
        }
      }
    }
  }

  //helper method to save an image to a specified path location
  private void saveImage(Image image, String imagePath) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    String ext = imagePath.split("\\.")[1];

    BufferedImage bufferedImage = new BufferedImage(width, height, 1);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = image.getPixel(j, i);
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        bufferedImage.setRGB(j, i, color.getRGB());
      }
    }
    ImageIO.write(bufferedImage, ext, new File(imagePath));
  }
}
