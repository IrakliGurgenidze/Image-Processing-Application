package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;
import model.utilities.HistogramUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public class HistogramCommand implements CommandController{

    private final ImageStorageModel imageStorageModel;

    /**
     * The constructor to initialize the histogram command.
     * @param imageStorageModel the state of the storage model
     */
    public HistogramCommand(ImageStorageModel imageStorageModel){
        this.imageStorageModel = imageStorageModel;
    }
    @Override
    public String execute(String[] args) throws IllegalArgumentException {
        if(args.length != 3){
            throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found"
                    + args.length + ". Correct usage: " + getUsage());
        }
        String sourceImageName = args[1];
        String destImageName = args[2];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);
        if(sourceImage == null){
            throw new IllegalArgumentException("Invalid request. Image with name + " + sourceImageName
                    + "not found.");
        }

        Image destImage = getHistogram(sourceImage, destImageName);
        imageStorageModel.insertImage(destImage);

        return "Completed histogram creation. File saved as: " + destImageName;
    }

    @Override
    public String getUsage() {
        return "histogram image-name dest-image-name: creates an histogram for the RGB intensities of the given" +
                "image. The image is saved in the database under the destination image name.";
    }

    private Image getHistogram(Image sourceImage, String destImageName){
        //uses the util method to get the buffered image histogram
        BufferedImage histogram = HistogramUtil.getHistogramImage(sourceImage);

        //similar to ImageUtil, the histogram is read into simple image format
        int width = histogram.getWidth();
        int height = histogram.getHeight();
        int red;
        int green;
        int blue;

        SimpleImage simpleHistogram = new SimpleImage(width, height, destImageName);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(histogram.getRGB(x, y));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                Pixel pixel = new Pixel(red, green, blue);
                simpleHistogram.setPixel(x, y, pixel);
            }
        }

        return simpleHistogram;
    }

}
