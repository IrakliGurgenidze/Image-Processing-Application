package controller_tests.command_tests;

import java.io.File;

import controller.script.ScriptController;
import controller.script.ScriptControllerImpl;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.StorageModel;
import view.script.ScriptView;
import view.script.ScriptViewImpl;

/**
 * Abstract class with helper methods for tests of commands.
 */
public abstract class AbstractCommandTest {

    protected final StorageModel imageModel = new ImageStorageModel();
    protected final ScriptView imageView = new ScriptViewImpl(System.in, System.out);
    protected final ScriptController imageController = new ScriptControllerImpl(imageModel, imageView);
    protected final String workingDirectory = setWd();


    /**
     * Used to test the equivalency of two images. All pixels in all locations should have the
     * same values in their RGB channels.
     * @param thisImg Image 1
     * @param otherImg Image 2
     * @return true if they are equal, false otherwise
     */
    public static boolean isEqual(Image thisImg, Image otherImg) {
        if (thisImg.getHeight() != otherImg.getHeight()) {
            return false;
        }

        if (thisImg.getWidth() != otherImg.getWidth()) {
            return false;
        }

        //check contents of each pixel
        for (int i = 0; i < thisImg.getHeight(); i++) {
            for (int j = 0; j < thisImg.getWidth(); j++) {
                Pixel thisPixel = thisImg.getPixel(j, i);
                Pixel otherPixel = otherImg.getPixel(j, i);
                if (thisPixel.getRed() != otherPixel.getRed()
                        || thisPixel.getGreen() != otherPixel.getGreen()
                        || thisPixel.getBlue() != otherPixel.getBlue()) {

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Used to set the working directory for consistency when dealing with unit test file paths.
     * @return String, the resulting working directory
     */
    public static String setWd() {
        return System.getProperty("user.dir")
                + File.separator
                + "test"
                + File.separator;
    }

    //helper method to load base image
    protected String[] loadImage(String fileName, String destImageName) {
        return imageController.parseCommand("load "
                + workingDirectory
                + "sample_images"
                + File.separator
                + fileName
                + " "
                + destImageName);
    }

    //helper method to determine whether a pixel is purely R, G, or B
    protected boolean isPureRGB(Pixel pixel) {
        if (pixel.getRed() != 0
                && pixel.getGreen() == 0
                && pixel.getBlue() == 0) {
            return true;
        } else if (pixel.getRed() == 0
                && pixel.getGreen() != 0
                && pixel.getBlue() == 0) {
            return true;
        } else return pixel.getRed() == 0
                && pixel.getGreen() == 0
                && pixel.getBlue() != 0;
    }


}
