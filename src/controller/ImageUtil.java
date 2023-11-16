package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Image;
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
  public static Image readColor(String fileName, String imageName) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(fileName));
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    int red;
    int green;
    int blue;

    Pixel[][] simpleImage = new Pixel[height][width];
    //SimpleImage simpleImage = new SimpleImage(width, height, imageName);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = new Color(bufferedImage.getRGB(x, y));
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        Pixel pixel = new Pixel(red, green, blue);
        simpleImage[y][x] = pixel;
        //simpleImage.setPixel(x, y, pixel);
      }
    }
    return new SimpleImage(imageName, simpleImage);
  }

  /**
   * This method reads a color image of PPM format and creates its generic model.SimpleImage
   * representation.
   *
   * @param fileName the name of the image file
   * @return the model. SimpleImage equivalent of the given image
   * @throws FileNotFoundException if the image file does not exist
   */
  public static Image readColorPPM(String fileName, String imageName)
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

    sc.nextInt();

    Pixel[][] simpleImage = new Pixel[height][width];
    //SimpleImage simpleImage = new SimpleImage(width, height, imageName);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel pixel = new Pixel(r, g, b);
        //simpleImage.setPixel(x, y, pixel);
        simpleImage[y][x] = pixel;
      }
    }

    return new SimpleImage(imageName, simpleImage);
  }
}

