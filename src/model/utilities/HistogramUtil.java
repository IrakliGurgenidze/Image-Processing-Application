package model.utilities;

import model.Image;
import model.Pixel;
import model.SimpleImage;

import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * This class contains the utility methods to draw a histogram on a buffered image.
 */
public class HistogramUtil {

    /**
     * Creates a BufferedImage histogram of the intensities of an image's RGB values.
     *
     * @param image the given image whose RGB values will be represented in a histogram
     * @return the BufferedImage form of the RGB histogram
     */
    public static BufferedImage getHistogramImage(Image image) {
        //height and width of histogram
        final int width = 256;
        final int height = 256;

        BufferedImage histogram = new BufferedImage(width, height, 1);
        Graphics graphics = histogram.getGraphics();
        graphics.fillRect(0,0,width,height);

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
            int scaledVal = (int) ((double) channels[0][i] / maxFreq*256);
            graphics.drawLine(i-1, 256-prev, i, 256-scaledVal);
            //track prev val to connect to new scaled val
            prev = scaledVal;
        }
        //reset prev val to -256 so that it scales to 0 to connect to first value
        prev = -256;
        graphics.setColor(Color.GREEN);
        for (int i = 0; i < channels[1].length; i++) {
            int scaledVal = (int) ((double) channels[1][i] / maxFreq*256);
            graphics.drawLine(i-1, 256-prev, i, 256-scaledVal);
            prev = scaledVal;
        }

        prev = -256;
        graphics.setColor(Color.BLUE);
        for (int i = 0; i < channels[2].length; i++) {
            int scaledVal = (int) ((double) channels[2][i] / maxFreq*256);
            graphics.drawLine(i-1, 256-prev, i, 256-scaledVal);
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

}
