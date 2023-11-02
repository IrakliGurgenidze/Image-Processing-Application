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
  public String execute(String[] args) {
    if (args.length != 3) {
      if (args.length > 3 && (!args[1].startsWith("\"") || (!args[1].endsWith("\""))
              || ((!args[2].startsWith("\"") || !args[2].endsWith("\""))))) {
        return "File path and image name must be enclosed in \"\" if they contain a space.";
      }
      return "Invalid input. Usage: " + getUsage();
    } else {
      String imagePath = args[1];
      String imageName = args[2];

      Image image = imageStorageModel.getImage(imageName);
      if (image == null) {
        return "Image with name " + imageName + " not found.";
      } else {
        try {
          saveImage(image, imagePath);
          return "Image saved to specified path.";
        } catch (IOException e) {
          return e.getMessage();
        }
      }
    }
  }

  @Override
  public String getUsage() {
    return "save image-path image-name: Save the image with the given name to the\n " +
            "specified path which should include the name of the file.";
  }

  //helper method to save an image to a specified path location
  private void saveImage(Image image, String imagePath) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    String[] split = imagePath.split("\\.");
    if (split.length != 2) {
      throw new IOException("Path does not include file extension.");
    }
    String ext = split[1];

    BufferedImage bufferedImage = new BufferedImage(width, height, 1);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = image.getPixel(j, i);
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        bufferedImage.setRGB(j, i, color.getRGB());
      }
    }
    try {
      ImageIO.write(bufferedImage, ext, new File(imagePath));
    } catch (IOException e) {
      throw new IOException("Path not valid.");
    }
  }
}
