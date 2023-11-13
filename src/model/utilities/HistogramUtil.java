package model.utilities;

import model.Image;
import model.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;


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
    public static BufferedImage getHistogram(Image image) {
        //height and width of histogram
        final int width = 256;
        final int height = 256;

        BufferedImage histogram = new BufferedImage(width, height, 1);
        Graphics graphics = histogram.getGraphics();

        //draw grid lines for histogram, currently every line is 1
        //FIXME might want to decrease grid lines
        for (int i = 0; i < 256; i++) {
            graphics.drawLine(i, 0, i, 200);
        }


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
        //draw the line for the value in each bin of each color
        graphics.setColor(Color.red);
        for (int i = 0; i < channels[0].length; i++) {
            graphics.drawLine(i, 256, i, 256 - channels[0][i]);
        }

        graphics.setColor(Color.green);
        for (int i = 0; i < channels[0].length; i++) {
            graphics.drawLine(i, 256, i, 256 - channels[1][i]);
        }

        graphics.setColor(Color.blue);
        for (int i = 0; i < channels[0].length; i++) {
            graphics.drawLine(i, 256, i, 256 - channels[2][i]);
        }
    }


}
