package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command color corrects an image to align all histogram peaks.
 */
public class ColorCorrectCommand implements CommandController{
    private final ImageStorageModel imageStorageModel;

    /**
     * This is the constructor to initialize the command.
     *
     * @param imageStorageModel state of image database
     */
    public ColorCorrectCommand(ImageStorageModel imageStorageModel) {
        this.imageStorageModel = imageStorageModel;
    }

    @Override
    public String execute(String[] args) throws IllegalArgumentException {
        if(args.length != 3) {
            throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
                    + args.length + ". Correct usage: " + getUsage());
        }

        String sourceImageName = args[1];
        String destImageName = args[2];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);
        if (sourceImage == null) {
            throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                    + " not found.");
        }

        Image colorCorrected = colorCorrect(sourceImage, destImageName);
        imageStorageModel.insertImage(colorCorrected);
        return "Color correction operation successful.";
    }

    @Override
    public String getUsage() {
        return null;
    }


    private Image colorCorrect(Image image, String destImageName){
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

    private Image applyOffset(Image image, int[] offsets, String destImageName){
        int width = image.getWidth();
        int height = image.getHeight();
        Image correctedImage = new SimpleImage(width, height, destImageName);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Pixel currPixel = image.getPixel(j, i);
                int currRed = currPixel.getRed();
                int currGreen = currPixel.getGreen();
                int currBlue = currPixel.getBlue();

                //apply offset value to each pixel
                currPixel.setRed(currRed + offsets[0]);
                currPixel.setGreen(currGreen + offsets[1]);
                currPixel.setBlue(currBlue + offsets[2]);

                correctedImage.setPixel(j,i,currPixel);
            }
        }
        return correctedImage;
    }

    private int[] getChannel(Image image, int channel) {
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

    private int averagePeakVal(int[] peaks){
        double sum = 0;
        for(int peak: peaks){
            sum += peak;
        }
        return (int)Math.round(sum/3);
    }

    private int findPeak(int[] channel){
        int peakLoc = 0;
        for(int i = 10; i < 245; i++) {
                if (channel[i] > channel[peakLoc]) {
                    peakLoc = i;
                }
        }
        //peak in this case is max peak on histogram
        return peakLoc;
    }
}
