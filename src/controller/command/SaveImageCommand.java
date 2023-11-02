package controller.command;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

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
    return "save image-path image-name: Save the image with the given name to the\n "
            + "specified path which should include the name of the file.";
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

    File outFile = new File(imagePath);
    File outDir = outFile.getParentFile();

    if (outDir != null) {
      try {
        Files.createDirectories(outDir.toPath());
      } catch (IOException e) {
        throw new IOException("Failed to create directory.");
      }
    }

    if (ext.equals("ppm")) {
      try (PrintWriter ppmWriter = new PrintWriter(imagePath)) {
        ppmWriter.println("P3");
        ppmWriter.println(width + " " + height);
        ppmWriter.println("255");

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            Pixel pixel = image.getPixel(j, i);
            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();
            ppmWriter.println(red + " " + green + " " + blue);
          }
        }
      } catch (IOException e) {
        throw new IOException("Failed to save PPM.");
      }
    } else {
      BufferedImage bufferedImage = new BufferedImage(width, height, 1);


      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Pixel pixel = image.getPixel(x, y);
          Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
          bufferedImage.setRGB(x, y, color.getRGB());
        }
      }


      try {
        ImageIO.write(bufferedImage, ext, outFile);
        bufferedImage.flush();
      } catch (IOException e) {
        throw new IOException("Path not valid.");
      }
    }
  }
}
