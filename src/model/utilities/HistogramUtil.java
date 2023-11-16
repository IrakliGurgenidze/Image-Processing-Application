package model.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

import model.Image;
import model.Pixel;

import model.SimpleImage;

/**
 * This class contains the utility methods to draw a histogram on a buffered image.
 */
public class HistogramUtil {

  /**
   * Method to get the RGB histogram of an image.
   * @param sourceImage Image, the image
   * @param destImageName String, the name of the new histogram image
   * @return the new histogram image
   */
  public static Image getHistogram(Image sourceImage, String destImageName) {
    //uses the util method to get the buffered image histogram
    BufferedImage histogram = getHistogramImage(sourceImage);

    //similar to ImageUtil, the histogram is read into simple image format
    int width = histogram.getWidth();
    int height = histogram.getHeight();
    int red;
    int green;
    int blue;

    Pixel[][] simpleHistogram = new Pixel[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = new Color(histogram.getRGB(x, y));
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        Pixel pixel = new Pixel(red, green, blue);
        simpleHistogram[y][x] = pixel;
      }
    }

    return new SimpleImage(destImageName, simpleHistogram);
  }

  /**
   * Method to get the color corrected version of an image.
   * @param image Image, the image to be corrected
   * @param destImageName String, the name of the new image
   * @return the new color corrected image
   */
  public static Image colorCorrect(Image image, String destImageName) {
    int[] redChannel = getChannel(image, 0);
    int[] greenChannel = getChannel(image, 1);
    int[] blueChannel = getChannel(image, 2);

    int redPeak = findPeak(redChannel);
    int greenPeak = findPeak(greenChannel);
    int bluePeak = findPeak(blueChannel);

    int avg = averagePeakVal(new int[]{redPeak, greenPeak, bluePeak});

    int redOffset = avg - redPeak;
    int greenOffset = avg - greenPeak;
    int blueOffset = avg - bluePeak;

    return applyOffset(image, new int[]{redOffset, greenOffset, blueOffset}, destImageName);
  }

  private static BufferedImage getHistogramImage(Image image) {
    //height and width of histogram
    final int width = 256;
    final int height = 256;

    BufferedImage histogram = new BufferedImage(width, height, 1);
    Graphics graphics = histogram.getGraphics();
    graphics.fillRect(0, 0, width, height);

    //draw grid lines for histogram, currently every line is 1
    //FIXME might want to decrease grid lines
        /*graphics.setColor(Color.BLACK);
        for (int i = 0; i < 256; i+=8) {
            graphics.drawLine(i, 0, i, 256);
        }*/


    //get the histogram for each channel
    int[] redHistogram = getChannel(image, 0);
    int[] greenHistogram = getChannel(image, 1);
    int[] blueHistogram = getChannel(image, 2);

    drawColors(new int[][]{redHistogram, greenHistogram, blueHistogram}, graphics);

    return histogram;
  }

  private static int[] getChannel(Image image, int channel) {
    //histograms with bin for each possible color intensity value (0-255)
    int[] histogram = new int[256];
    int width = image.getWidth();
    int height = image.getHeight();
    int intensity = -1;

    //for every pixel in the image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = image.getPixel(j, i);
        //channel equates to color in order of RGB
        switch (channel) {
          //set intensity to proper color's intensity
          case 0:
            intensity = pixel.getRed();
            break;
          case 1:
            intensity = pixel.getGreen();
            break;
          case 2:
            intensity = pixel.getBlue();
            break;
        }
        //increment bin at value of intensity of color
        histogram[intensity]++;
      }
    }
    return histogram;
  }

  private static void drawColors(int[][] channels, Graphics graphics) {
    //need to get max freq val to scale image vertically
    int maxFreq = getMaxFrequency(channels);

    int prev = -256;
    //draw the line for the value in each bin of each color
    graphics.setColor(Color.RED);
    for (int i = 0; i < channels[0].length; i++) {
      //the scaled y value is just the (channel value / maxFreq * 256) since image is 256x256
      int scaledVal = (int) ((double) channels[0][i] / maxFreq * 256);
      graphics.drawLine(i - 1, 256 - prev, i, 256 - scaledVal);
      //track prev val to connect to new scaled val
      prev = scaledVal;
    }
    //reset prev val to -256 so that it scales to 0 to connect to first value
    prev = -256;
    graphics.setColor(Color.GREEN);
    for (int i = 0; i < channels[1].length; i++) {
      int scaledVal = (int) ((double) channels[1][i] / maxFreq * 256);
      graphics.drawLine(i - 1, 256 - prev, i, 256 - scaledVal);
      prev = scaledVal;
    }

    prev = -256;
    graphics.setColor(Color.BLUE);
    for (int i = 0; i < channels[2].length; i++) {
      int scaledVal = (int) ((double) channels[2][i] / maxFreq * 256);
      graphics.drawLine(i - 1, 256 - prev, i, 256 - scaledVal);
      prev = scaledVal;
    }
  }

  private static int getMaxFrequency(int[][] arrays) {
    int max = 0;
    for (int[] array : arrays) {
      for (int value : array) {
        if (value > max) {
          max = value;
        }
      }
    }
    return max;
  }

  private static Image applyOffset(Image image, int[] offsets, String destImageName) {
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] correctedImage = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = image.getPixel(j, i);
        int currRed = currPixel.getRed();
        int currGreen = currPixel.getGreen();
        int currBlue = currPixel.getBlue();

        //apply offset value to each pixel
        currPixel.setRed(currRed + offsets[0]);
        currPixel.setGreen(currGreen + offsets[1]);
        currPixel.setBlue(currBlue + offsets[2]);


        correctedImage[i][j] = currPixel;
      }
    }
    return new SimpleImage(destImageName, correctedImage);
  }

  private static int averagePeakVal(int[] peaks) {
    double sum = 0;
    for (int peak : peaks) {
      sum += peak;
    }
    return (int) Math.round(sum / 3);
  }

  private static int findPeak(int[] channel) {
    int peakLoc = 0;
    for (int i = 10; i < 245; i++) {
      if (channel[i] > channel[peakLoc]) {
        peakLoc = i;
      }
    }
    //peak in this case is max peak on histogram
    return peakLoc;
  }

}
