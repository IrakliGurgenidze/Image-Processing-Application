package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import controller.ImageUtil;

/**
 * This class represents the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public class ImageStorageModel implements StorageModel {

  //list of images loaded during this session
  private final Map<String, Image> images = new Hashtable<>();

  @Override
  public Image getImage(String name) {
    return images.get(name);
  }

  @Override
  public void insertImage(Image image) {
    images.put(image.getName(), image);
  }

  @Override
  public void removeImage(String imageName) throws IllegalArgumentException {
    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with name "
              + imageName);
    }

    images.remove(imageName);
  }

  @Override
  public int getSize() {
    return this.images.size();
  }

  @Override
  public void loadImage(String loadPath, String imageName) throws IllegalArgumentException {
    Image loadedImg;
    try {
      if (loadPath.split("\\.")[1].equals("ppm")) {
        loadedImg = ImageUtil.readColorPPM(loadPath, imageName);
      } else {
        loadedImg = ImageUtil.readColor(loadPath, imageName);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    insertImage(loadedImg);
  }

  @Override
  public void saveImage(String savePath, String imageName) throws IllegalArgumentException,
          IOException {
    Image image = getImage(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image with name " + imageName + " does not " +
              "exist in database.");
    }
    int height = image.getHeight();
    int width = image.getWidth();
    String[] split = savePath.split("\\.");
    if (split.length != 2) {
      throw new IllegalArgumentException("Path does not include file extension.");
    }
    String ext = split[1];

    File outFile = new File(savePath);
    File outDir = outFile.getParentFile();

    if (outDir != null) {
      try {
        Files.createDirectories(outDir.toPath());
      } catch (IOException e) {
        throw new IOException("Failed to create directory.");
      }
    }

    if (ext.equals("ppm")) {
      try (PrintWriter ppmWriter = new PrintWriter(savePath)) {
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
