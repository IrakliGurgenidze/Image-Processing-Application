package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
     * Helper method to read an image from a file, into an object of type Image.
     *
     * @param loadPath  String, filepath to load from
     * @param imageName String, the name by which to store the image
     * @return the loaded Image
     * @throws IllegalArgumentException on invalid filepath
     */
    public static Image loadImage(String loadPath, String imageName) throws IllegalArgumentException {
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

        return loadedImg;
    }

    /**
     * This method reads a color image of non-PPM format and creates its generic model. SimpleImage
     * representation.
     *
     * @param fileName the name of the image file
     * @return the model.SimpleImage equivalent of the given image
     * @throws IOException if the image file does not exist
     */
    private static Image readColor(String fileName, String imageName) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(fileName));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        int red;
        int green;
        int blue;

        Pixel[][] simpleImage = new Pixel[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                Pixel pixel = new Pixel(red, green, blue);
                simpleImage[y][x] = pixel;
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
    private static Image readColorPPM(String fileName, String imageName)
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

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = sc.nextInt();
                int g = sc.nextInt();
                int b = sc.nextInt();
                Pixel pixel = new Pixel(r, g, b);
                simpleImage[y][x] = pixel;
            }
        }

        return new SimpleImage(imageName, simpleImage);
    }

    /**
     * helper method to save a given image to a specific file location.
     *
     * @param image    image to be saved
     * @param savePath path to save image to
     * @throws IOException on error writing to provided location
     */
    public static void saveImage(Image image, String savePath) throws IOException {
        int height = image.getHeight();
        int width = image.getWidth();
        String[] split;
        if (savePath.contains(".")) {
            split = savePath.split("\\.");
        } else {
            savePath += ".png";
            split = savePath.split("\\.");
        }
        String ext = split[1];
        File outFile = new File(savePath);

        if (ext.equals("ppm")) {
            try (PrintWriter ppmWriter = new PrintWriter(savePath)) {
                ppmWriter.println("P3");
                ppmWriter.println(width + " " + height);
                ppmWriter.println("255");

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Pixel pixel = image.getPixel(x, y);
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

