import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * This method reads a color image of non-PPM format and creates its generic ColorImage
   * representation.
   *
   * @param fileName the name of the image file
   * @return the ColorImage equivalent of the given image
   * @throws IOException if the image file does not exist
   */
  public static ColorImage readColor(String fileName, String imageName) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(fileName));

    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    int red;
    int green;
    int blue;

    ColorImage colorImage = new ColorImage(width, height, imageName);
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        Color color = new Color(bufferedImage.getRGB(j,i));
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        Pixel pixel = new Pixel(red, green, blue);
        colorImage.setPixel(j,i,pixel);
      }
    }

    return colorImage;
  }

  /**
   * This method reads a color image of PPM format and creates its generic ColorImage representation.
   *
   * @param fileName the name of the image file
   * @return the ColorImage equivalent of the given image
   * @throws FileNotFoundException if the image file does not exist
   */
  public static ColorImage readColorPPM(String fileName, String imageName)
          throws FileNotFoundException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + fileName + " not found.");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
     throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    ColorImage colorImage = new ColorImage(width, height, imageName);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel pixel = new Pixel(r,g,b);
        colorImage.setPixel(j,i,pixel);
      }
    }

    return colorImage;
  }
}

