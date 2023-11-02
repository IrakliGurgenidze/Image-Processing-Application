package model.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Pixel;
import model.SimpleImage;


/**
 * This class contains utility methods to read images from file.
 */
public class ImageUtil {

  /**
   * This method reads a color image of non-PPM format and creates its generic model. SimpleImage
   * representation.
   *
   * @param fileName the name of the image file
   * @return the model.SimpleImage equivalent of the given image
   * @throws IOException if the image file does not exist
   */
  public static SimpleImage readColor(String fileName, String imageName) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(fileName));
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    int red;
    int green;
    int blue;

    SimpleImage simpleImage = new SimpleImage(width, height, imageName);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color color = new Color(bufferedImage.getRGB(j, i));
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        Pixel pixel = new Pixel(red, green, blue);
        simpleImage.setPixel(j, i, pixel);
      }
    }

    return simpleImage;
  }

  /**
   * This method reads a color image of PPM format and creates its generic model.SimpleImage representation.
   *
   * @param fileName the name of the image file
   * @return the model. SimpleImage equivalent of the given image
   * @throws FileNotFoundException if the image file does not exist
   */
  public static SimpleImage readColorPPM(String fileName, String imageName)
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

    SimpleImage simpleImage = new SimpleImage(width, height, imageName);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel pixel = new Pixel(r, g, b);
        simpleImage.setPixel(j, i, pixel);
      }
    }

    return simpleImage;
  }
}

