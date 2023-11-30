package model;

import model.utilities.CompressionUtil;
import model.utilities.HistogramUtil;
import model.utilities.LevelsAdjustUtil;

/**
 * Simple image implementation of Image interface.
 */
public class SimpleImage implements Image {

    //width and height of image, measured in pixels
    private final int width;
    private final int height;

    //name of image
    private final String name;

    //pixels in image
    private final Pixel[][] imageBody;

    /**
     * Constructor for simple image class.
     *
     * @param width  int, width of image
     * @param height int, height of image
     * @param name   String, name of image
     * @throws IllegalArgumentException if non-positive width and height entered
     */
    public SimpleImage(int width, int height, String name) throws IllegalArgumentException {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Width and Height must be positive integers.");
        }

        this.width = width;
        this.height = height;
        this.name = name;
        this.imageBody = new Pixel[height][width];
    }

    /**
     * The constructor to create an image from an array of pixels.
     *
     * @param name      String, name of image
     * @param imageBody String, pixel array to become the body of the image
     * @throws IllegalArgumentException if the image body is of illegal dimensions
     */
    public SimpleImage(String name, Pixel[][] imageBody) throws IllegalArgumentException {
        int width = imageBody[0].length;
        int height = imageBody.length;

        if (width < 1) {
            throw new IllegalArgumentException("Width and Height must be positive integers.");
        }

        this.width = width;
        this.height = height;
        this.name = name;
        this.imageBody = imageBody;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Pixel getPixel(int x, int y) throws IndexOutOfBoundsException {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            throw new IndexOutOfBoundsException("Coordinates are out of bounds.");
        }

        //return a deep copy of the selected pixel
        return new Pixel(imageBody[y][x]);
    }

    @Override
    public Image getRedComponent(String resultImageName) {
        Pixel[][] redImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                redImage[y][x] = new Pixel(currPixel.getRed(), 0, 0);
            }
        }

        return new SimpleImage(resultImageName, redImage);
    }

    @Override
    public Image getGreenComponent(String resultImageName) {
        Pixel[][] greenImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                greenImage[y][x] = new Pixel(0, currPixel.getGreen(), 0);
            }
        }

        return new SimpleImage(resultImageName, greenImage);
    }

    @Override
    public Image getBlueComponent(String resultImageName) {
        Pixel[][] blueImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                blueImage[y][x] = new Pixel(0, 0, currPixel.getBlue());
            }
        }

        return new SimpleImage(resultImageName, blueImage);
    }

    @Override
    public Image getValueComponent(String resultImageName) {
        Pixel[][] valueImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                valueImage[y][x] = new Pixel(currPixel.getValue());
            }
        }

        return new SimpleImage(resultImageName, valueImage);
    }

    @Override
    public Image getIntensityComponent(String resultImageName) {
        Pixel[][] intensityImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                intensityImage[y][x] = new Pixel((int) currPixel.getIntensity());
            }
        }

        return new SimpleImage(resultImageName, intensityImage);
    }

    @Override
    public Image getLumaComponent(String resultImageName) {
        Pixel[][] lumaImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                lumaImage[y][x] = new Pixel((int) currPixel.getLuma());
            }
        }

        return new SimpleImage(resultImageName, lumaImage);
    }

    @Override
    public Image getHorizontalFlip(String resultImageName) {
        Pixel[][] horizontalImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);

                //horizontal flip -> new x coordinate
                int flippedX = width - x - 1;
                horizontalImage[y][flippedX] = currPixel;
            }
        }
        return new SimpleImage(resultImageName, horizontalImage);
    }

    @Override
    public Image getVerticalFlip(String resultImageName) {
        Pixel[][] verticalImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);
                //vertical flip -> new y coordinate
                int flippedY = height - y - 1;
                verticalImage[flippedY][x] = currPixel;
            }
        }
        return new SimpleImage(resultImageName, verticalImage);
    }

    @Override
    public Image brighten(int increment, String resultImageName) {
        Pixel[][] brightenedImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                Pixel currPixel = this.getPixel(x, y);

                int red = currPixel.getRed() + increment;
                int green = currPixel.getGreen() + increment;
                int blue = currPixel.getBlue() + increment;

                brightenedImage[y][x] = new Pixel(red, green, blue);
            }
        }
        return new SimpleImage(resultImageName, brightenedImage);
    }

    @Override
    public Image applyFilter(double[][] kernel, String resultImageName) {
        Pixel[][] filteredImage = new Pixel[height][width];
        int filterSize = kernel.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double redSum = 0.0;
                double greenSum = 0.0;
                double blueSum = 0.0;

                //compute the filter value for a given pixel
                for (int i = -filterSize / 2; i <= filterSize / 2; i++) {
                    for (int j = -filterSize / 2; j <= filterSize / 2; j++) {
                        if (x + j >= 0 && x + j < width && y + i >= 0 && y + i < height) {
                            Pixel neighbor = this.getPixel(x + j, y + i);

                            redSum += neighbor.getRed() * kernel[i + filterSize / 2][j + filterSize / 2];
                            greenSum += neighbor.getGreen() * kernel[i + filterSize / 2][j + filterSize / 2];
                            blueSum += neighbor.getBlue() * kernel[i + filterSize / 2][j + filterSize / 2];
                        }
                    }
                }
                Pixel filteredPixel = new Pixel((int) redSum, (int) greenSum, (int) blueSum);
                filteredImage[y][x] = filteredPixel;
            }
        }
        return new SimpleImage(resultImageName, filteredImage);
    }

    @Override
    public Image applyLinearColorTransformation(double[][] kernel, String resultImageName) {
        Pixel[][] transformedImage = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel currPixel = this.getPixel(x, y);

                int red = (int) (kernel[0][0] * currPixel.getRed()
                        + kernel[0][1] * currPixel.getGreen()
                        + kernel[0][2] * currPixel.getBlue());

                int green = (int) (kernel[1][0] * currPixel.getRed()
                        + kernel[1][1] * currPixel.getGreen()
                        + kernel[1][2] * currPixel.getBlue());

                int blue = (int) (kernel[2][0] * currPixel.getRed()
                        + kernel[2][1] * currPixel.getGreen()
                        + kernel[2][2] * currPixel.getBlue());

                transformedImage[y][x] = new Pixel(red, green, blue);
            }
        }

        return new SimpleImage(resultImageName, transformedImage);
    }

    @Override
    public Image compressImage(int compressionRatio, String compressedImageName) {
        return CompressionUtil.compress(compressionRatio, this, compressedImageName);
    }

    @Override
    public Image getHistogram(String histogramName) {
        return HistogramUtil.getHistogram(this, histogramName);
    }

    @Override
    public Image colorCorrectImage(String resultImageName) {
        return HistogramUtil.colorCorrect(this, resultImageName);
    }

    @Override
    public Image adjustLevels(String resultImageName, int b, int m, int w) {
        return LevelsAdjustUtil.levelsAdjust(this, b, m, w, resultImageName);
    }
}
