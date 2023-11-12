package model.utilities;

import model.Image;
import model.SimpleImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HistogramUtil {

    public static SimpleImage getHistogram(Image image){
        final int width = 256;
        final int height  = 256;

        BufferedImage histogram = new BufferedImage(width, height, 1);
        Graphics g = histogram.getGraphics();

        return null;
    }


}
